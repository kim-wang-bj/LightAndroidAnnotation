package com.wq.android.lightannotation.annotations;

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
 * See: {@link android.support.v4.view.ViewPager#addOnPageChangeListener(ViewPager.OnPageChangeListener)}
 *
 * Usage Example:
 *
 * &#064;OnPageScrolled({R.id.viewId})
 * private void onPageScrolled(View v, int position, float positionOffset, int positionOffsetPixels)
 * {
 * }
 *
 * </pre>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnPageScrolled {
    int[] value() default View.NO_ID;
}
