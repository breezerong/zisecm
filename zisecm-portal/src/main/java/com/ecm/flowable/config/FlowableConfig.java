package com.ecm.flowable.config;

import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecm.flowable.method.RouteMethod;


/**
 * 为解决flowable图片中的中文乱码
 *
 * @author puhaiyang
 * @date 2018/12/19
 */

@Configuration
public class FlowableConfig implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {


    @Override
    public void configure(SpringProcessEngineConfiguration engineConfiguration) {
        engineConfiguration.setActivityFontName("宋体");
        engineConfiguration.setLabelFontName("宋体");
        engineConfiguration.setAnnotationFontName("宋体");
    }
	@Bean
	public ProcessDiagramGenerator processDiagramGenerator() {
		return new CustomProcessDiagramGenerator();
	}
    @Bean(name = "routeMethod")
    public RouteMethod auditMethod(){
        return new RouteMethod();
    }
}
