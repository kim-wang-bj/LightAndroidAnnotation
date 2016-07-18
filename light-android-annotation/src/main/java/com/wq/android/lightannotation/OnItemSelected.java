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
 * See: {@link android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android.widget.AdapterView, android.view.View, int, long)}
 * 
 * Usage Example:	
 * 
 * &#064;OnItemSelected({R.id.viewId})
 * private void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
 * {
 * }
 * 
 * </pre>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnItemSelected {
	public int[] value() default View.NO_ID;
}
