package com.ufreedom.lubanadapter;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;

/**
 * Represents an Android resource. Used with {@link ResourceProcessor}
 * <p>
 * Taken from Butterknife. https://github.com/JakeWharton/butterknife/pull/613
 */
final class AndroidResource {

    private static final ClassName ANDROID_R = ClassName.get("android", "R");

    private final int value;
    private final CodeBlock code;
    private final boolean qualifed;
    private final String resourceName;
    private final ClassName className;

    AndroidResource(int value) {
        this.value = value;
        this.code = CodeBlock.of("$L", value);
        this.qualifed = false;
        resourceName = null;
        className = null;
    }

    AndroidResource(int value,ClassName className, String resourceName) {
        this.className = className;
        this.resourceName = resourceName;
        this.value = value;
        this.code = className.topLevelClassName().equals(ANDROID_R)
                ? CodeBlock.of("$L.$N", className, resourceName)
                : CodeBlock.of("$T.$N", className, resourceName);
        this.qualifed = true;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof AndroidResource && value == ((AndroidResource) o).value;
    }

    @Override
    public int hashCode() {
        return value;
    }


    public int getValue() {
        return value;
    }

    public String getResourceName() {
        return resourceName;
    }

    public ClassName getClassName() {
        return className;
    }

    CodeBlock getCode() {
        return code;
    }

}
