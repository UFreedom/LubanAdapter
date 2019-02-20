package com.ufreedom.demo;

import android.content.Context;

import com.ufreedom.demo.holder.ImageHolder;
import com.ufreedom.demo.holder.ImageTextMixedHolder;
import com.ufreedom.demo.model.ImageModel;
import com.ufreedom.demo.model.ImageTextMixedModel;
import com.ufreedom.lubanadapter.Holder;
import com.ufreedom.lubanadapter.Holders;
import com.ufreedom.lubanadapter.IRVModel;
import com.ufreedom.lubanadapter.LubanRecyclerViewAdapter;

import java.util.List;

/**
 * Author: UFreedom
 * Email: sunfreedomsun@gmail.com
 * Since 2019/2/20
 */
@Holders({
        @Holder(layout = R.layout.view_item_image_text_mixed, model = ImageTextMixedModel.class, holder = ImageTextMixedHolder.class),
        @Holder(layout = R.layout.view_item_image, model = ImageModel.class, holder = ImageHolder.class)
})
public class DemoAdapter extends LubanRecyclerViewAdapter {

    private List<IRVModel> modelList;

    public DemoAdapter(Context context) {
        super(context);
    }

    public void setModelList(List<IRVModel> modelList) {
        this.modelList = modelList;
    }

    @Override
    public List<?> getModelList() {
        return modelList;
    }
}
