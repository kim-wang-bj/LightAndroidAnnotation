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
 * See: {@link ViewPager#addOnPageChangeListener(ViewPager.OnPageChangeListener)}
 *
 * Usage Example:
 *
 * &#064;OnPageScrollStateChanged({R.id.viewId})
 * private void onPageScrollStateChanged(View v, int state)
 * {
 * }
 *
 * </pre>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnPageScrollStateChanged {
    int[] value() default View.NO_ID;
}
