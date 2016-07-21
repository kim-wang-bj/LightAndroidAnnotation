package com.wq.android.lightannotation;

import android.support.v4.view.ViewPager;
import android.view.View;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *
 * See: {@link ViewPager#addOnPageChangeListener(ViewPager.OnPageChangeListener)}
 *
 * Usage Example:
 *
 * &#064;OnPageSelected({R.id.viewId})
 * private void onPageSelected(View v, int position)
 * {
 * }
 *
 * </pre>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnPageSelected {
    int[] value() default View.NO_ID;
}
