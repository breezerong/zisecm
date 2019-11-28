package com.ecm.portal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

public class SpringUtil {

	
	private static ApplicationContext applicationContext;
	public static void setAppcxt(ApplicationContext applicationContext) {
		SpringUtil.applicationContext=applicationContext;
	}
    

    // 通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    // 通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }


	
}
