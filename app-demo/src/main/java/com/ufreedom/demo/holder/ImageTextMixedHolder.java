package com.ufreedom.demo.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ufreedom.demo.R;
import com.ufreedom.demo.model.ImageTextMixedModel;
import com.ufreedom.lubanadapter.LubanViewHolder;

/**
 * Author: UFreedom
 * Email: sunfreedomsun@gmail.com
 * Since 2019/2/20
 */
public class ImageTextMixedHolder extends LubanViewHolder<ImageTextMixedModel> {

    private ImageView imageView;

    private TextView textView;

    public ImageTextMixedHolder(View itemView) {
        super(itemView);
        imageView = findViewById(R.id.image_view);
        textView = findViewById(R.id.text_view);

    }

    @Override
    public void onBindViewHolder(ImageTextMixedModel imageTextMixedModel, int position) {
        imageView.setImageResource(imageTextMixedModel.resId);
        textView.setText(imageTextMixedModel.content);
    }

}
