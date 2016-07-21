package com.wq.android.lightannotation;

import android.view.View;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * See: {@link android.view.GestureDetector.SimpleOnGestureListener#onFling(android.view.MotionEvent, android.view.MotionEvent, float, float)}
 *
 * Usage Example:
 *
 * &#064;OnFling({R.id.id1, R.id.id2 })
 * private boolean onFling(View v, MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
 * {
 * }
 * </pre>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnFling {
    int[] value() default View.NO_ID;
}
