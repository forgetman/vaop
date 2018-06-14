package vaop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yuansui
 * @since 2018/6/9
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface SingleClick {
}
