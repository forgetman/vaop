package vaop.annotation.util;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        ThreadType.SINGLE,
        ThreadType.FIXED,
        ThreadType.DISK,
        ThreadType.NETWORK,
})
@Retention(RetentionPolicy.RUNTIME)
public @interface ThreadType {

    /**
     * 单线程池
     */
    int SINGLE = 0;

    /**
     * 多线程池
     */
    int FIXED = 1;

    /**
     * 磁盘读写线程池(本质上是单线程池）
     */
    int DISK = 2;

    /**
     * 网络请求线程池(本质上是多线程池）
     */
    int NETWORK = 3;
}