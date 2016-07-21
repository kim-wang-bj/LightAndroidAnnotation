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
 * See: {@link android.view.View.OnClickListener#onClick(android.view.View)}
 *
 * Usage Example:
 *
 * &#064;OnClick({R.id.viewId})
 * private void onClick(View v)
 * {
 * }
 *
 * </pre>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnClick {
    public int[] value() default View.NO_ID;
}
