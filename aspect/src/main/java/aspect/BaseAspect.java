package aspect;

/**
 * @author yuansui
 * @since 2018/5/11
 */
abstract class BaseAspect {
    static final String START = "execution(@aspect.annotation.";
    static final String END_METHOD = " * *(..))";
    static final String END_CONSTRUCTOR = " *.new(..))";
    static final String START_SYNTHETIC = "execution(!synthetic * *(..)) && ";

    /**
     * 其他的只能使用范例模板复制粘贴使用了
     * WITHIN: "@within(aspect.annotation.注解类名)||@annotation(aspect.annotation.注解类名)";
     */
}
