package io.github.andhikayuana.processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 9/13/17
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Greet {
    String[] value() default "";
}
