package com.ufreedom.lubanadapter;

import com.squareup.javapoet.ClassName;

/**
 * Created by UFreedom on 2019/2/17.
 */

class Constants {

    static final ClassName ADAPTER_PROXY = ClassName.get("com.ufreedom.lubanadapter", "IAdapterProxy");

    static final ClassName LUBAN_VIEW_HOLDER = ClassName.get("com.ufreedom.lubanadapter", "LubanViewHolder");

    static final ClassName VIEW_GROUP = ClassName.get("android.view", "ViewGroup");

    static final ClassName VIEW = ClassName.get("android.view", "View");

    static final ClassName LAYOUT_INFLATER = ClassName.get("android.view", "LayoutInflater");


}
