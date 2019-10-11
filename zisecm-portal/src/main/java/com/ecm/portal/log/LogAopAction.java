package com.ecm.portal.log;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @ClassName  LogAopAction   
 * @Description TODO(日志切面)   
 * @author yaozhigang
 * @date 2018年7月17日 下午2:13:45  
 *
 */
@Aspect
@Component
public class LogAopAction {
	private final Logger logger = LoggerFactory.getLogger(LogAopAction.class);
	
	//切入点设置到自定义的log注解上
	//@Pointcut("execution(public * com.ecm.portal.controller..*(..))")
    @Pointcut("@annotation(com.ecm.portal.log.LogAnnotation)")
    private void pointCutMethod(){}
	
	/**
     * 记录操作日志
     */
    @After("pointCutMethod()")  // 使用上面定义的切入点
    public void recordLog(JoinPoint joinPoint){
        Long start = System.currentTimeMillis();
       
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
      
        //下面开始获取 targetType，remark，action
        try {
        	Object[] args = joinPoint.getArgs();
        	String methodName = joinPoint.getSignature().getName();
            String targetName = joinPoint.getTarget().getClass().getName();
            
            Class targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();
            for (Method method : methods){
                if(method.getName().equals(methodName)){
                    LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
                    logger.info("method param: {}",args[0].toString());
                    logger.info("action: {},remark: {}",logAnnotation.action(),logAnnotation.remark());
                    logger.info("methodName: {},targetName: {}",methodName,targetName);
                }
            }
        }catch (Exception e){
            logger.error("插入日志异常",e.getMessage());
        }
        Long end = System.currentTimeMillis();
        logger.info("记录日志消耗时间:"+ (end - start) / 1000);
    }
}
