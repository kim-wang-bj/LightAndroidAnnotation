package com.wq.android.lightannotation;

import android.view.View;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *
 * See: {@link android.widget.ListView#setOnItemClickListener(android.widget.AdapterView.OnItemClickListener)}
 *
 * Usage Example:
 *
 * &#064;OnItemClick({R.id.viewId})
 * private void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
 * {
 * }
 *
 * </pre>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnItemClick {
    public int[] value() default View.NO_ID;
}
