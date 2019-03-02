package com.ufreedom.lubanadapter;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by UFreedom on 2019/2/17.
 *
 */

public class AdapterProxyFinder {

    private static final String TAG = "AdapterProxyFinder";
    private static boolean debug = false;

    @VisibleForTesting
    private static final Map<Class<?>, Constructor<? extends IAdapterProxy>> ADAPTER_PROXY_MAP = new LinkedHashMap<>();


    /**
     * Control whether debug logging is enabled.
     */
    public static void setDebug(boolean debug) {
        AdapterProxyFinder.debug = debug;
    }

    public static IAdapterProxy inject(@NonNull Object target) {
        Class<?> targetClass = target.getClass();
        if (debug) Log.d(TAG, "Looking up adapter proxy for " + targetClass.getName());
        Constructor<? extends IAdapterProxy> constructor = findAdapterProxyConstructorForClass(targetClass);

        if (constructor == null) {
            throw new NullPointerException("Do Not find adapter proxy for " + targetClass.getName()
                    + ",Please check you have use @ViewType or @ViewTypes for you adapter");
        }
        try {
            return constructor.newInstance();
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to invoke " + constructor, e);
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to invoke " + constructor, e);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            if (cause instanceof Error) {
                throw (Error) cause;
            }
            throw new RuntimeException("Unable to create adapter proxy instance.", cause);
        }

    }

    @Nullable
    @CheckResult
    @UiThread
    private static Constructor<? extends IAdapterProxy> findAdapterProxyConstructorForClass(Class<?> cls) {
        Constructor<? extends IAdapterProxy> adapterProxyCtor = ADAPTER_PROXY_MAP.get(cls);
        if (adapterProxyCtor != null || ADAPTER_PROXY_MAP.containsKey(cls)) {
            if (debug) Log.d(TAG, "Find in adapter proxy cache map.");
            return adapterProxyCtor;
        }

        String clsName = cls.getName();
        if (clsName.startsWith("android.")
                || clsName.startsWith("java.")
                || clsName.startsWith("androidx.")
                || clsName.startsWith("android.support.")) {
            if (debug) Log.d(TAG, "Reached framework class. Abandoning search.");
            return null;
        }

        try {
            Class<?> adapterProxyClass = cls.getClassLoader().loadClass(clsName + "_AdapterProxy");
            //noinspection unchecked
            adapterProxyCtor = (Constructor<? extends IAdapterProxy>) adapterProxyClass.getConstructor();
            if (debug) Log.d(TAG, "Loaded adapter proxy class and constructor.");
        } catch (ClassNotFoundException e) {
            if (debug) Log.d(TAG, "Not found. Trying superclass " + cls.getSuperclass().getName());
            adapterProxyCtor = findAdapterProxyConstructorForClass(cls.getSuperclass());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Unable to find adapter proxy constructor for " + clsName, e);
        }
        ADAPTER_PROXY_MAP.put(cls, adapterProxyCtor);
        return adapterProxyCtor;
    }
}
