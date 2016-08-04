package com.wq.android.lightannotation.annotations;

import android.view.View;
import android.widget.CompoundButton;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *
 * See: {@link CompoundButton#setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener)}
 *
 * Usage Example:
 *
 * &#064;OnCheckedChanged({R.id.viewId})
 * private void method(CompoundButton buttonView, boolean isChecked)
 * {
 * }
 *
 * </pre>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnCheckedChanged {
    public int[] value() default View.NO_ID;
}
