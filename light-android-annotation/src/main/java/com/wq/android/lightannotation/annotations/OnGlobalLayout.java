package com.wq.android.lightannotation.annotations;

import android.view.View;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *
 * See: {@link View#getViewTreeObserver}
 *
 * Usage Example:
 *
 * &#064;OnGlobalLayout({R.id.viewId})
 * private boolean onGlobalLayoutChanged(View v) {
 * 	return true;
 * }
 *
 * </pre>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnGlobalLayout {
    int[] value() default View.NO_ID;
}
