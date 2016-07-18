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
 * See: {@link android.view.View#setOnLongClickListener(android.view.View.OnLongClickListener)}
 * 
 * Usage Example:	
 * 
 * &#064;OnLongClick({R.id.viewId})
 * public boolean onLongClick(View v)
 * {
 * }
 * 
 * </pre>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnLongClick
{
	public int[] value() default View.NO_ID;
}
