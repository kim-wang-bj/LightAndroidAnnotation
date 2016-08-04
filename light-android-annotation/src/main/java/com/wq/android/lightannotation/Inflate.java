package com.wq.android.lightannotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Wang Qi</br></br>
 *         <p>
 *         Usage example: </br><code>&#064;Inflate(R.layoutId) private View view;</code>
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Inflate {
    int value();
    int parent();
}
