package com.ufreedom.lubanadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by UFreedom on 2019/2/17.
 * <p>
 * Adapter Proxy, Usage for create view holders
 */
public interface IAdapterProxy {

    /**
     * Proxy for RecyclerView.Adapter.onCreateViewHolder(ViewGroup, int)
     */
    LubanViewHolder createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

    /**
     * Proxy for RecyclerView.Adapter.getItemViewType(int)
     */
    int getItemViewType(int position, Object model);


}
