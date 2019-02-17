package com.ufreedom.lubanadapter.listener;

import android.view.View;

import com.ufreedom.lubanadapter.LubanViewHolder;

/**
 * Created by UFreedom on 2019/2/17.
 */
public interface OnItemLongClickListener {

    boolean onItemLongClick(View view, LubanViewHolder lubanViewHolder, int position);

}
