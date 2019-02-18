package com.ufreedom.lubanadapter;

/**
 * Created by UFreedom on 2/17/19.
 */
public interface IViewHolder<MODEL> {


    void onBindViewHolder(MODEL model, int position);

    /**
     * Called when a view created by this adapter has been attached to a window.
     * <p>
     * <p>This can be used as a reasonable signal that the view is about to be seen
     * by the user.
     */
    void onViewAttachedToWindow();

    /**
     * Called when a view created by this adapter has been detached from its window.
     * <p>
     * <p>Becoming detached from the window is not necessarily a permanent condition;
     * the consumer of an Adapter's views may choose to cache views offscreen while they
     * are not visible, attaching and detaching them as appropriate.</p>
     */
    void onViewDetachedFromWindow();
}
