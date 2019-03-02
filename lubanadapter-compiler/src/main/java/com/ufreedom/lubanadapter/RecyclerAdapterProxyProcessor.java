package com.ufreedom.lubanadapter;

import com.google.auto.common.SuperficialValidation;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.sun.tools.javac.code.Attribute;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import static com.google.auto.common.MoreElements.getPackage;
import static javax.lang.model.element.ElementKind.CLASS;
import static javax.lang.model.element.Modifier.PRIVATE;

/**
 * Created by UFreedom on 2019/2/17.
 */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class RecyclerAdapterProxyProcessor extends AbstractProcessor {

    private Filer filer;
    private Elements elementUtils;
    private Types typeUtils;
    private ResourceProcessor resourceProcessor;
    private Map<TypeElement,List<ViewTypeMapElement>> holderInfoMap = new HashMap<>();
    private int defaultTypeValue = 10000;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        filer = processingEnv.getFiler();
        elementUtils = processingEnv.getElementUtils();
        typeUtils = processingEnv.getTypeUtils();

        resourceProcessor = new ResourceProcessor(processingEnvironment, elementUtils, typeUtils);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        resourceProcessor.processorResources(roundEnvironment);

        holderInfoMap.clear();

        collectionInfo(roundEnvironment);
        writeToFile();

        return false;
    }

    private static AnnotationMirror getAnnotationMirror(TypeElement typeElement, Class<?> clazz) {
        String clazzName = clazz.getName();
        for (AnnotationMirror m : typeElement.getAnnotationMirrors()) {
            if (m.getAnnotationType().toString().equals(clazzName)) {
                return m;
            }
        }
        return null;
    }

    private static AnnotationValue getAnnotationValue(AnnotationMirror annotationMirror, String key) {
        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues().entrySet()) {
            if (entry.getKey().getSimpleName().toString().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    private AnnotationValue getValue(TypeElement foo, Class<?> clazz, String key) {
        AnnotationMirror am = getAnnotationMirror(foo, clazz);
        if (am == null) {
            return null;
        }
        return getAnnotationValue(am, key);
    }


    private ClassName getClassName(AnnotationValue annotationValue) {
        return (ClassName) ClassName.get(((Attribute.Class) annotationValue).getValue());
    }

    private boolean validateAnnotationClass(Class<? extends Annotation> annotationClass, Element element) {

        if (!SuperficialValidation.validateElement(element)) {
            return false;
        }

        boolean isValid = true;

        // Verify containing type.
        if (element.getKind() != CLASS) {
            error(element, "@%s may only be contained in classes.",
                    annotationClass.getSimpleName());
            isValid = false;
        }

        // Verify containing class visibility is not private.
        if (element.getModifiers().contains(PRIVATE)) {
            error(element, "@%s may not be contained in private classes.",
                    annotationClass.getSimpleName());
            isValid = false;
        }

        return isValid;
    }


    private void collectionInfo(RoundEnvironment env) {
        BindType holderType;

        for (Element element : env.getElementsAnnotatedWith(BindType.class)) {

            if (!validateAnnotationClass(BindType.class, element)) continue;

            holderType = element.getAnnotation(BindType.class);

            AnnotationValue modelClassAnnotation = getValue((TypeElement) element, BindType.class, "model");
            AnnotationValue holderClassAnnotation = getValue((TypeElement) element, BindType.class, "holder");

            AndroidResource layoutResource = resourceProcessor.getResourceForValue(holderType.layout());
            List<ViewTypeMapElement> holderInfoList = new ArrayList<>();
            ViewTypeMapElement holderInfo;
            if (holderType.position() != BindType.POSITION_NONE) {
                holderInfo = new ViewTypeMapElement(holderType.position(), getClassName(modelClassAnnotation));
            } else {
                holderInfo = new ViewTypeMapElement(getClassName(modelClassAnnotation), getClassName(holderClassAnnotation));
            }
            holderInfo.layoutCodeBlock = layoutResource.getCode();

            holderInfoList.add(holderInfo);

            TypeElement typeElement = (TypeElement) element;
            holderInfoMap.put(typeElement, holderInfoList);

        }

        for (Element element : env.getElementsAnnotatedWith(BindTypes.class)) {

            if (!validateAnnotationClass(BindTypes.class, element)) continue;

            List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();

            AnnotationMirror holdTypesMirrors = annotationMirrors.get(0);
            Attribute.Array attributes = (Attribute.Array) holdTypesMirrors.getElementValues().values().toArray()[0];

            List<ViewTypeMapElement> holderInfoList = new ArrayList<>();

            for (Attribute value : attributes.values) {

                AnnotationMirror annotationMirror = (AnnotationMirror) value;

                AnnotationValue modelValue = getAnnotationValue(annotationMirror, "model");
                AnnotationValue holderValue = getAnnotationValue(annotationMirror, "holder");
                AnnotationValue layoutValue = getAnnotationValue(annotationMirror, "layout");
                AnnotationValue positionValue = getAnnotationValue(annotationMirror, "position");

                ViewTypeMapElement holderInfo;
                Integer layout = Integer.parseInt(layoutValue.toString());
                AndroidResource layoutResource = resourceProcessor.getResourceForValue(layout);

                if (positionValue != null) {
                    holderInfo = new ViewTypeMapElement(Integer.parseInt(positionValue.getValue().toString()), getClassName(holderValue));
                } else {
                    holderInfo = new ViewTypeMapElement(getClassName(modelValue), getClassName(holderValue));
                }
                holderInfo.layoutCodeBlock = layoutResource.getCode();

                holderInfoList.add(holderInfo);

            }

            TypeElement typeElement = (TypeElement) element;
            holderInfoMap.put(typeElement, holderInfoList);
        }
    }


    private void writeToFile() {

        //Generate adapter proxy
        for (TypeElement element : holderInfoMap.keySet()) {

            String packageName = getPackage(element).getQualifiedName().toString();
            String className = element.getQualifiedName().toString().substring(
                    packageName.length() + 1).replace('.', '$');
            ClassName proxyClassName = ClassName.get(packageName, className + "_AdapterProxy");

            TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(proxyClassName.simpleName())
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(Constants.ADAPTER_PROXY);

            MethodSpec defaultCtor = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .build();
            typeSpecBuilder.addMethod(defaultCtor);

            List<ViewTypeMapElement> holderInfoList = holderInfoMap.get(element);

            //Filter position -> holder, model -> holder
            List<ViewTypeMapElement> position2HolderList = new ArrayList<>();
            List<ViewTypeMapElement> model2HolderList = new ArrayList<>();
            for (ViewTypeMapElement holderElement : holderInfoList) {
                if (holderElement.isByPosition()) {
                    position2HolderList.add(holderElement);
                } else {
                    model2HolderList.add(holderElement);
                }
            }

            //1.Generate item type constant
            for (ViewTypeMapElement holderElement : holderInfoList) {
                FieldSpec typeField = FieldSpec.builder(int.class, "ITEM_TYPE_" + defaultTypeValue)
                        .addModifiers(Modifier.PRIVATE, Modifier.FINAL,Modifier.STATIC)
                        .initializer("$L",defaultTypeValue)
                        .build();
                typeSpecBuilder.addField(typeField);
                holderElement.generateType = defaultTypeValue;
                defaultTypeValue++;
            }

            //2.Generate getItemViewType(int position) method
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("getItemViewType")
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .addParameter(int.class,"position")
                    .addParameter(Object.class,"model")
                    .returns(int.class);

            //2-1. position -> holder
            for (ViewTypeMapElement holderElement : position2HolderList) {
                methodBuilder.beginControlFlow("if (position == $L)",holderElement.position)
                        .addStatement("return ITEM_TYPE_$L",holderElement.generateType)
                        .endControlFlow();
            }


            //2-2. model -> holder
            for (ViewTypeMapElement holderElement : model2HolderList) {
                methodBuilder.beginControlFlow("if (model instanceof $T)",holderElement.modelClass)
                        .addStatement("return ITEM_TYPE_$L",holderElement.generateType)
                        .endControlFlow();
            }
            methodBuilder.addStatement("return -1");
            typeSpecBuilder.addMethod(methodBuilder.build());


            //3.Generate onCreateViewHolder() method
            MethodSpec.Builder methodBuilder2 = MethodSpec.methodBuilder("createViewHolder")
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .addParameter(Constants.LAYOUT_INFLATER,"inflater")
                    .addParameter(Constants.VIEW_GROUP,"parent")
                    .addParameter(int.class,"viewType")
                    .returns(Constants.BASE_VIEW_HOLDER)
                    .addStatement("$T view", Constants.VIEW);

            for (ViewTypeMapElement holderElement : holderInfoList) {
                methodBuilder2.beginControlFlow("if (viewType == ITEM_TYPE_$L)",holderElement.generateType)
                        .addStatement("view = inflater.inflate($L, parent, false)",holderElement.layoutCodeBlock)
                        .addStatement("return new $T(view)",holderElement.holderClass)
                        .endControlFlow();
            }
            methodBuilder2.addStatement("return null");
            typeSpecBuilder.addMethod(methodBuilder2.build());


            //Final generate java class file
            JavaFile javaFile = JavaFile.builder(proxyClassName.packageName(), typeSpecBuilder.build())
                    .addFileComment("Generated code from LubanAdapter. Do not modify")
                    .build();
            try {
                javaFile.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    static Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();

        annotations.add(BindType.class);
        annotations.add(BindTypes.class);

        return annotations;
    }

    private void error(Element element, String message, Object... args) {
        printMessage(Diagnostic.Kind.ERROR, element, message, args);
    }

    private void printMessage(Diagnostic.Kind kind, Element element, String message, Object[] args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(kind, message, element);
    }


}
