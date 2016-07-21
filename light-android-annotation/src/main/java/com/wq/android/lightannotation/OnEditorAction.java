package com.wq.android.lightannotation;

/**
 * Created by qwang on 2016/7/13.
 */

import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *
 * See: {@link android.widget.TextView.OnEditorActionListener#onEditorAction(TextView v, int actionId, KeyEvent event)}
 *
 * Usage Example:
 *
 * &#064;OnEditorAction({R.id.viewId, R.id.viewId})
 * private boolean onEditorAction(TextView v, int actionId, KeyEvent event)
 * {
 * }
 *
 * </pre>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnEditorAction {
    int[] value() default View.NO_ID;
}

