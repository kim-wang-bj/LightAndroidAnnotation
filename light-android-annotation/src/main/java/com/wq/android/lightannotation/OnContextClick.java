package com.wq.android.lightannotation;

import android.view.MotionEvent;
import android.view.View;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * See: {@link android.view.GestureDetector.SimpleOnGestureListener#onContextClick(MotionEvent)}
 * 
 * Usage Example:	
 * 
 * &#064;OnContextClick({R.id.id, R.id.id2})
 * private boolean onContextClick(MotionEvent e) {
	return false;
   }
 * </pre>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnContextClick {
	public int[] value() default View.NO_ID;
}
