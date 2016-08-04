package com.wq.android.lightannotation.annotations;

import android.view.View;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * See: {@link android.view.GestureDetector.SimpleOnGestureListener#onLongPress(android.view.MotionEvent)}
 *
 * Usage Example:
 *
 * &#064;OnLongPress({R.id.id1, R.id.id2 })
 * private void onLongPress(View v, MotionEvent e)
 * {
 * }
 * </pre>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnLongPress {
    int[] value() default View.NO_ID;
}
