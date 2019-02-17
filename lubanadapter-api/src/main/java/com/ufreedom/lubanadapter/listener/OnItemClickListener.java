package com.ufreedom.lubanadapter.listener;

import android.view.View;

import com.ufreedom.lubanadapter.LubanViewHolder;


/**
 * Created by UFreedom on 2019/2/17.
 *
 * Click Listener for recycler view holder item
 *
 */

public interface OnItemClickListener {

    void onItemClick(View view, LubanViewHolder lubanViewHolder, int position);

}
