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
 * See: {@link android.view.View#getViewTreeObserver}
 * 
 * Usage Example:	
 * 
 * &#064;OnPreDraw({R.id.viewId})
 * private boolean onPreDraw(View v)
 * {
 * }
 * 
 * </pre>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnPreDraw {
	public int[] value() default View.NO_ID;
}
