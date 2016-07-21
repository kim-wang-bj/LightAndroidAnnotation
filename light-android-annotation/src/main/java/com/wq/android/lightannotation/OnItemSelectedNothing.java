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
 * See: {@link android.widget.ListView#setOnItemSelectedListener(android.widget.AdapterView.OnItemSelectedListener)}
 * See: {@link android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(android.widget.AdapterView)}
 *
 * Usage Example:
 *
 * &#064;OnItemSelectedNothing({R.id.viewId})
 * private void onItemSelectedNothing(android.widget.AdapterView view)
 * {
 * }
 *
 * </pre>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnItemSelectedNothing {
    int[] value() default View.NO_ID;
}
