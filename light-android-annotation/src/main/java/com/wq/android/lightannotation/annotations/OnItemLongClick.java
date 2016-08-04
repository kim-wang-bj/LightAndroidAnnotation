package com.wq.android.lightannotation.annotations;

import android.view.View;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *
 * See: {@link android.widget.ListView#setOnItemLongClickListener(android.widget.AdapterView.OnItemLongClickListener)}
 *
 * Usage Example:
 *
 * &#064;OnItemLongClick({R.id.viewId})
 * private boolean OnItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
 * {
 * }
 *
 * </pre>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnItemLongClick {
    int[] value() default View.NO_ID;
}
