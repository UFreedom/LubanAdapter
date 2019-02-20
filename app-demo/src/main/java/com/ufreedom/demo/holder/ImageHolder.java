package com.ufreedom.demo.holder;

import android.view.View;
import android.widget.ImageView;

import com.ufreedom.demo.R;
import com.ufreedom.demo.model.ImageModel;
import com.ufreedom.lubanadapter.LubanViewHolder;

/**
 * Author: UFreedom
 * Email: sunfreedomsun@gmail.com
 * Since 2019/2/20
 */
public class ImageHolder extends LubanViewHolder<ImageModel> {

    private ImageView imageView;

    public ImageHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image_view);
    }

    @Override
    public void onBindViewHolder(ImageModel imageModel, int position) {
        imageView.setImageResource(imageModel.resId);
    }
}
