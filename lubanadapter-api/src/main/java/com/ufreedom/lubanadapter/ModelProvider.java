package com.ufreedom.lubanadapter;

/**
 * Created by UFreedom on 2019/2/17.
 */

public interface ModelProvider<T> {

    T getModel(int position);

}
