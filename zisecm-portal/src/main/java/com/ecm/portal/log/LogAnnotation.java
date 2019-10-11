package com.ecm.portal.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName  LogAnnotation   
 * @Description TODO(自定义的log注解,在方法上加上这个注解才会进行拦截)   
 * @author yaozhigang
 * @date 2018年7月17日 下午2:30:50  
 *
 */
@Retention(RetentionPolicy.RUNTIME)//注解会在class中存在，运行时可通过反射获取
@Target(ElementType.METHOD)//目标是方法
@Documented//文档生成时，该注解将被包含在javadoc中，可去掉
public @interface LogAnnotation {
    String action() default "";
    String targetType() default "";
    String remark() default "";
}
