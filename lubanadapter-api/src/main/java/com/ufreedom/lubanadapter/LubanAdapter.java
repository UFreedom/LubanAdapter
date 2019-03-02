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
 */

public abstract class LubanAdapter<T> extends RecyclerView.Adapter<LubanViewHolder> implements ModelProvider<T> {

    protected LayoutInflater mInflater;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnItemDoubleClickListener onItemDoubleClickListener;
    private IAdapterProxy mAdapterProxy;
    private List<T> dataList;

    public LubanAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mAdapterProxy = findAdapterProxy();
    }

    protected IAdapterProxy findAdapterProxy() {
        return AdapterProxyFinder.inject(this);
    }

    @NonNull
    @Override
    public LubanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IViewHolder viewHolder = null;
        viewHolder = mAdapterProxy.createViewHolder(mInflater, parent, viewType);

        if (!(viewHolder instanceof LubanViewHolder)) {
            throw new IllegalStateException(String.format("Please make %s extends LubanViewHolder when use LubanAdapter"
                    , viewHolder.getClass().getName()));
        }

        LubanViewHolder lubanViewHolder = (LubanViewHolder) viewHolder;
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
    public int getItemViewType(int position) {
        return mAdapterProxy.getItemViewType(position, getModel(position));
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public T getModel(int position) {
        if (dataList == null || dataList.size() == 0) {
            return null;
        }
        return dataList.get(position);
    }

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

}
