package com.ufreedom.demo;

import android.content.Context;

import com.ufreedom.demo.holder.ImageHolder;
import com.ufreedom.demo.holder.ImageTextMixedHolder;
import com.ufreedom.demo.model.ImageModel;
import com.ufreedom.demo.model.ImageTextMixedModel;
import com.ufreedom.lubanadapter.BindType;
import com.ufreedom.lubanadapter.BindTypes;
import com.ufreedom.lubanadapter.IRVModel;
import com.ufreedom.lubanadapter.LubanAdapter;

/**
 * Author: UFreedom
 * Email: sunfreedomsun@gmail.com
 * Since 2019/2/20
 */
@BindTypes({
        @BindType(layout = R.layout.view_item_image_text_mixed, model = ImageTextMixedModel.class, holder = ImageTextMixedHolder.class),
        @BindType(layout = R.layout.view_item_image, model = ImageModel.class, holder = ImageHolder.class)
})
public class DemoAdapter extends LubanAdapter<IRVModel> {


    public DemoAdapter(Context context) {
        super(context);
    }


}
