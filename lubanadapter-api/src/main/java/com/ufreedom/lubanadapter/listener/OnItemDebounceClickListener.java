package com.ufreedom.lubanadapter.listener;

import android.os.SystemClock;
import android.view.View;

import com.ufreedom.lubanadapter.LubanViewHolder;

/**
 * Created by UFreedom on 2019/2/17.
 */
public abstract class OnItemDebounceClickListener implements OnItemClickListener {

    private long lastClickTime = 0;

    private int minIntervalInMillis;

    public OnItemDebounceClickListener() {
        minIntervalInMillis = 1000;
    }

    /**
     * The debounce interval times
     */
    public OnItemDebounceClickListener(int intervalInMillis) {
        this.minIntervalInMillis = intervalInMillis;
    }

    @Override
    public void onItemClick(View view, LubanViewHolder lubanViewHolder, int position) {
        if (lastClickTime == 0) {
            lastClickTime = SystemClock.elapsedRealtime();
            onDebounceClick(view, lubanViewHolder, position);
        } else {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            long timeInterval = elapsedRealtime - lastClickTime;
            if (timeInterval > minIntervalInMillis) {
                lastClickTime = elapsedRealtime;
                onDebounceClick(view, lubanViewHolder, position);
            }
        }
    }


    public abstract void onDebounceClick(View view, LubanViewHolder lubanViewHolder, int position);

}
