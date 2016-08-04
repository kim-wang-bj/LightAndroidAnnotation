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
 * See: {@link android.view.View#getViewTreeObserver}
 *
 * Usage Example:
 *
 * &#064;OnScrollChanged({R.id.viewId})
 * private void onScrollChanged(View v)
 * {
 * }
 *
 * </pre>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnScrollChanged {
    int[] value() default View.NO_ID;
}
