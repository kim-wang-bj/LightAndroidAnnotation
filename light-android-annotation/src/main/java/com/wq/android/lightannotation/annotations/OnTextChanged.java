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
 * See: {@link android.text.TextWatcher#onTextChanged(CharSequence, int, int, int)}
 *
 * Usage Example:
 *
 * &#064;OnTextChanged(R.id.id1, R.id.id2)
 * private void onTextChanged(View, CharSequence, int, int, int)
 * {
 * }
 *
 * </pre>
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnTextChanged {
    int[] value() default View.NO_ID;
}
