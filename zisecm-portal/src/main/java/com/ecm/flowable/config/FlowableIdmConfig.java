package com.ecm.flowable.config;

import org.flowable.idm.engine.IdmEngineConfiguration;
import org.flowable.idm.spring.SpringIdmEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecm.flowable.identity.CustomGroupEntityManager;
import com.ecm.flowable.identity.CustomUserEntityManager;

 /**
 * @author simon liao
 *保留，暂未使用
 */
public class FlowableIdmConfig implements EngineConfigurationConfigurer<SpringIdmEngineConfiguration> {

	@Override
	public void configure(SpringIdmEngineConfiguration configuration) {
		configuration.setGroupEntityManager(customGroupEntityManager(configuration));
		configuration.setUserEntityManager(customUserEntityManager(configuration));
	}

	@Bean
	public CustomGroupEntityManager customGroupEntityManager(IdmEngineConfiguration configuration) {
		return new CustomGroupEntityManager(configuration, configuration.getGroupDataManager());
	}

	@Bean
	public CustomUserEntityManager customUserEntityManager(IdmEngineConfiguration configuration) {
		return new CustomUserEntityManager(configuration, configuration.getUserDataManager());
	}
}
