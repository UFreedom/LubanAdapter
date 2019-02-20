package com.ufreedom.demo.model;

import com.ufreedom.lubanadapter.IRVModel;

/**
 * Created by UFreedom on 2/18/19.
 */
public class ImageTextMixedModel extends ImageModel {


    public String content;

    public ImageTextMixedModel(int resId, String content) {
        super(resId);
        this.content = content;
    }
}
