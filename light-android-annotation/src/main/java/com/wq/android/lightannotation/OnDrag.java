package com.wq.android.lightannotation;

import android.view.DragEvent;
import android.view.View;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *
 * See: {@link android.view.View.OnDragListener#onDrag(View, DragEvent)}
 *
 * Usage Example:
 *
 * &#064;OnClick({R.id.viewId})
 * private boolean OnDrag(View v, DragEvent) {
 *  return true;
 * }
 *
 * </pre>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnDrag {
    int[] value() default View.NO_ID;
}
