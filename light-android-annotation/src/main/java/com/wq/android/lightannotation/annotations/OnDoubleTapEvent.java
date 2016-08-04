package com.wq.android.lightannotation.annotations;

import android.view.View;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * See: {@link android.view.GestureDetector.SimpleOnGestureListener#onDoubleTapEvent(android.view.MotionEvent)}
 *
 * Usage Example:
 *
 * &#064;OnDoubleTapEvent({ R.id.id1, R.id.id2 })
 * private boolean onDoubleTapEvent(View v, MotionEvent e)
 * {
 * }
 * </pre>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnDoubleTapEvent {
    int[] value() default View.NO_ID;
}
