package cn.mykine.mall.goods.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于自定义缓存的注解
 * */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyCacheable {

    /**
     * 缓存的名称前缀，完整的缓存名称生成规则：{cacheName}:{key}
     * @return
     * */
    String cacheName();

    /**
     * 缓存Key的表达式，可以使用SpEL表达式，用于匹配参数值
     * @return
     * */
    String key();

    /**
     * 缓存的过期时间，单位为秒，默认不设置过期时间
     * */
    int expireInSeconds() default 0;

}
