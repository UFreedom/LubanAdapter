package com.ufreedom.demo;

import android.content.Context;

import com.ufreedom.lubanadapter.IRVModel;
import com.ufreedom.lubanadapter.LubanAdapter;

import java.util.List;

/**
 * Created by UFreedom on 2/18/19.
 */
/*@BindTypes({
        @BindType(layout = R.layout.view_item_1, model = Model1.class, holder = ModelHolder1.class),
        @BindType(layout = R.layout.view_item_2, model = Model2.class, holder = ModelHolder2.class),
})*/
public class MainDemoAdapter extends LubanAdapter {

    private List<IRVModel> irvModelList;

    public MainDemoAdapter(Context context) {
        super(context);
    }

    public void setIrvModelList(List<IRVModel> irvModelList) {
        this.irvModelList = irvModelList;
    }


}
