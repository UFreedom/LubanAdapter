package com.ufreedom.lubanadapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;

/**
 * Author: UFreedom
 * Email: sunfreedomsun@gmail.com
 * Since 2019/3/2
 */
public class LubanAdapterHelper {

    private HashMap<Class, ViewType> class2HolderMap;
    private SparseArray<ViewType> viewTypeSparseArray;
    private Context context;

    private LubanAdapterHelper(Context context) {
        this.context = context;
        class2HolderMap = new HashMap<>();
        viewTypeSparseArray = new SparseArray<>();
    }

    public static LubanAdapterHelper create(Context context) {
        return new LubanAdapterHelper(context);
    }


    public LubanAdapterHelper register(Class modelClas, int layoutRes, Class<? extends LubanViewHolder> holderClass) {
        class2HolderMap.put(modelClas, new ViewType(layoutRes, holderClass));
        return this;
    }

    public <T> LubanAdapter<T> apply() {
        Collection<ViewType> values = class2HolderMap.values();
        int startType = 1;
        for (ViewType value : values) {
            value.type = com.ufreedom.lubanadapter.ViewType.VIEW_TYPE_EXTENSIONS + startType;
            viewTypeSparseArray.put(value.type, value);
            startType++;
        }
        return new LubanAdapter<T>(context) {
            @Override
            protected IAdapterProxy findAdapterProxy() {
                return new AdapterProxyInner();
            }
        };
    }


    class ViewType {

        int layoutId;

        Class<? extends LubanViewHolder> holderClass;

        int type;

        ViewType(int layoutId, Class<? extends LubanViewHolder> holderClass) {
            this.layoutId = layoutId;
            this.holderClass = holderClass;
        }
    }

    class AdapterProxyInner implements IAdapterProxy {

        @Override
        public LubanViewHolder createViewHolder(LayoutInflater inflater, ViewGroup parent, int vt) {
            ViewType viewType = viewTypeSparseArray.get(vt);
            View view = inflater.inflate(viewType.layoutId, parent, false);
            LubanViewHolder lubanViewHolder = null;
            try {
                Class<?> holderClass = viewType.holderClass;
                Constructor<?> holderCtor = holderClass.getDeclaredConstructor(View.class);
                holderCtor.setAccessible(true);
                lubanViewHolder = (LubanViewHolder) holderCtor.newInstance(view);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return lubanViewHolder;
        }

        @Override
        public int getItemViewType(int position, Object model) {
            ViewType viewType = class2HolderMap.get(model.getClass());
            if (viewType == null) {
                throw new IllegalStateException(String.format("This class[%s] not register", model.getClass()));
            }
            return viewType.type;
        }
    }
}
