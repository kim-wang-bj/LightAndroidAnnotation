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
 * See: {@link android.text.TextWatcher#afterTextChanged(android.text.Editable)}
 *
 * Usage Example:
 *
 * &#064;AfterTextChanged(R.id.id1, R.id.id2)
 * private void afterTextChanged(View, android.text.Editable)
 * {
 * }
 *
 * </pre>
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AfterTextChanged {
    int[] value() default View.NO_ID;
}
