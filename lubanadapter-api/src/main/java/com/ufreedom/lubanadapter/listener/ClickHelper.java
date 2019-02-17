package com.ufreedom.lubanadapter.listener;

import android.annotation.SuppressLint;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by UFreedom on 2019/2/17.
 */
public class ClickHelper {

    public static void listen(final View view,  final OnSingleClick onSingleClick,final OnDoubleClick onDoubleClick,final OnLongClick onLongClick) {
        final GestureDetector gd = new GestureDetector(view.getContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (onSingleClick != null) {
                    onSingleClick.onSingleClick(view);
                    return true;
                }
                return super.onSingleTapConfirmed(e);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (onDoubleClick != null) {
                    onDoubleClick.onDoubleClick(view);
                    return true;
                }
                return super.onDoubleTap(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                if (onLongClick != null) {
                    onLongClick.onLongClick(view);
                }
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gd.onTouchEvent(event);
            }
        });

    }

    public interface OnDoubleClick {
        void onDoubleClick(View view);
    }


    public interface OnSingleClick {
        void onSingleClick(View view);
    }

    public interface OnLongClick {
        void onLongClick(View view);
    }
}
