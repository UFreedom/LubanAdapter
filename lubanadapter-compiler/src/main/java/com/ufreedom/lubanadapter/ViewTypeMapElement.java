package com.ufreedom.lubanadapter;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;

/**
 * Created by Sun Meng on 2019/2/17.
 */

class ViewTypeMapElement {

    int position;

    ClassName modelClass;

    ClassName holderClass;

    CodeBlock layoutCodeBlock;

    int generateType;


    ViewTypeMapElement(int position, ClassName holderClass) {
        this.position = position;
        this.holderClass = holderClass;
    }

    ViewTypeMapElement(ClassName modelClass, ClassName holderClass) {
        this.modelClass = modelClass;
        this.holderClass = holderClass;
        position = BindType.POSITION_NONE;
    }

    boolean isByPosition() {
        return position != BindType.POSITION_NONE;
    }
}
