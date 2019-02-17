package com.ufreedom.lubanadapter;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;

/**
 * Created by Sun Meng on 2019/2/17.
 */

class HolderMapElement {

    int position;

    ClassName modelClass;

    ClassName holderClass;

    CodeBlock layoutCodeBlock;

    int generateType;


    HolderMapElement(int position, ClassName holderClass) {
        this.position = position;
        this.holderClass = holderClass;
    }

    HolderMapElement(ClassName modelClass, ClassName holderClass) {
        this.modelClass = modelClass;
        this.holderClass = holderClass;
        position = Holder.POSITION_NONE;
    }

    boolean isByPosition() {
        return position != Holder.POSITION_NONE;
    }
}
