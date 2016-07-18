package com.wq.android.lightannotation;

import android.view.View;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * See: {@link android.view.GestureDetector.SimpleOnGestureListener#onShowPress(android.view.MotionEvent)}
 * 
 * Usage Example:	
 * 
 * &#064;OnShowPress({ R.id.id1, R.id.id2 })
 * private void onShowPress(View v, MotionEvent e) {
 * }
 * </pre>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnShowPress {
	public int[] value() default View.NO_ID;
}
