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
 * See: {@link android.view.View#setOnKeyListener(android.view.View.OnKeyListener)}
 * 
 * Usage Example:	
 * 
 * &#064;OnKey({R.id.viewId})
 * public void onKey(View v, int keyCode, KeyEvent event)
 * {
 * }
 * 
 * </pre>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnKey
{
	public int[] value() default View.NO_ID;
}
