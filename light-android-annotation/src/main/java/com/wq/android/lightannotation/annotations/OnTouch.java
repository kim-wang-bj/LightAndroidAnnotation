package com.wq.android.lightannotation.annotations;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * See: {@link android.view.View#setOnTouchListener(android.view.View.OnTouchListener)}
 *
 *  Usage example:
 *
 * &#064;OnTouch(R.id.onTouch)
 *  private void onTouch(View v, MotionEvent e)
 *  {
 *  }
 *
 * </pre>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnTouch {
    int[] value() default View.NO_ID;
}
