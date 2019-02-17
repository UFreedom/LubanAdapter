package com.ufreedom.lubanadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ufreedom.lubanadapter.listener.OnItemClickListener;
import com.ufreedom.lubanadapter.listener.OnItemDoubleClickListener;
import com.ufreedom.lubanadapter.listener.OnItemLongClickListener;

import java.util.List;

/**
 * Created by UFreedom on 2019/2/17.
 *
 */

public abstract class LubanRecyclerViewAdapter extends RecyclerView.Adapter<LubanViewHolder> implements ModelProvider {

    protected LayoutInflater mInflater;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnItemDoubleClickListener onItemDoubleClickListener;
    private IAdapterProxy mAdapterProxy;

    public LubanRecyclerViewAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mAdapterProxy = LubanAdapter.inject(this);
    }

    @NonNull
    @Override
    public LubanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LubanViewHolder lubanViewHolder = mAdapterProxy.createViewHolder(mInflater, parent, viewType);
        onViewHolderCreated(lubanViewHolder, parent, viewType);
        if (onItemClickListener != null) {
            lubanViewHolder.setOnItemClickListener(onItemClickListener);
        }

        if (onItemLongClickListener != null) {
            lubanViewHolder.setOnItemLongClickListener(onItemLongClickListener);
        }

        if (onItemDoubleClickListener != null) {
            lubanViewHolder.setOnItemDoubleClickListener(onItemDoubleClickListener);
        }

        return lubanViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LubanViewHolder holder, int position) {
        holder.onBindViewHolder(getModel(position), position);
    }

    @Override
    public int getItemCount() {
        if (getModelList() == null) return 0;
        return getModelList().size();
    }

    @Override
    public int getItemViewType(int position) {
        return mAdapterProxy.getItemViewType(position,getModel(position));
    }

    public abstract List<?> getModelList();

    protected void onViewHolderCreated(LubanViewHolder viewHolder, ViewGroup parent, int viewType) {

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setOnItemDoubleClickListener(OnItemDoubleClickListener onItemDoubleClickListener) {
        this.onItemDoubleClickListener = onItemDoubleClickListener;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull LubanViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.onViewAttachedToWindow();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull LubanViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.onViewDetachedFromWindow();
    }

    @Override
    public Object getModel(int position) {
        return getModelList().get(position);
    }
}
