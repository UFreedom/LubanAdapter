package com.ufreedom.lubanadapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by UFreedom on 2019/2/17.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Holder {

    int POSITION_NONE = -1;

    int position() default POSITION_NONE;

    Class<?> model() default ModelNull.class;

    Class<?> holder();

    int layout();
}
