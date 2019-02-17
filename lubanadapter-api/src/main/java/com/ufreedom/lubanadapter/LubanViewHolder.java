package com.ufreedom.lubanadapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ufreedom.lubanadapter.listener.ClickHelper;
import com.ufreedom.lubanadapter.listener.OnItemClickListener;
import com.ufreedom.lubanadapter.listener.OnItemDoubleClickListener;
import com.ufreedom.lubanadapter.listener.OnItemLongClickListener;

/**
 * Created by UFreedom on 2019/2/17.
 */

public abstract class LubanViewHolder<MODEL> extends RecyclerView.ViewHolder {


    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public LubanViewHolder(View itemView) {
        super(itemView);
    }

    public void setOnItemClickListener(final OnItemClickListener listener) {
        if (listener != null) {
            onItemClickListener = listener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, LubanViewHolder.this, getAdapterPosition());
                }
            });
        }
    }

    public void setOnItemLongClickListener(final OnItemLongClickListener listener) {
        if (listener != null) {
            onItemLongClickListener = listener;
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return listener.onItemLongClick(itemView, LubanViewHolder.this, getAdapterPosition());
                }
            });
        }
    }

    public void setOnItemDoubleClickListener(final OnItemDoubleClickListener listener) {
        ClickHelper.listen(itemView, new ClickHelper.OnSingleClick() {
            @Override
            public void onSingleClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(itemView, LubanViewHolder.this, getAdapterPosition());
                }
            }
        }, new ClickHelper.OnDoubleClick() {
            @Override
            public void onDoubleClick(View view) {
                if (listener != null) {
                    listener.onItemDoubleClick(itemView, LubanViewHolder.this, getAdapterPosition());
                }
            }
        }, new ClickHelper.OnLongClick() {
            @Override
            public void onLongClick(View view) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick(view, LubanViewHolder.this, getAdapterPosition());
                }
            }
        });
    }

    public abstract void onBindViewHolder(MODEL model, int position);


    protected Context getContext() {
        return itemView.getContext();
    }

    protected View findViewById(int id) {
        return itemView.findViewById(id);
    }


    protected float getDimension(@DimenRes int id) {
        return getResources().getDimension(id);
    }

    protected int getDimensionPixelSize(@DimenRes int id) {
        return getResources().getDimensionPixelSize(id);
    }

    protected String getString(@StringRes int id) {
        return getResources().getString(id);
    }

    protected String getString(@StringRes int id, Object... formatArgs) {
        return getResources().getString(id, formatArgs);
    }

    protected Resources getResources() {
        return getContext().getResources();
    }

    protected int getColor(@ColorRes int id) {
        return getResources().getColor(id);
    }

    /**
     * Called when a view created by this adapter has been attached to a window.
     * <p>
     * <p>This can be used as a reasonable signal that the view is about to be seen
     * by the user.
     */
    protected void onViewAttachedToWindow() {

    }

    /**
     * Called when a view created by this adapter has been detached from its window.
     * <p>
     * <p>Becoming detached from the window is not necessarily a permanent condition;
     * the consumer of an Adapter's views may choose to cache views offscreen while they
     * are not visible, attaching and detaching them as appropriate.</p>
     */
    protected void onViewDetachedFromWindow() {

    }

}
