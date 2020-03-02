package com.ecm.portal;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;


@SpringBootApplication	/*(exclude = {SecurityAutoConfiguration.class})*/
@ComponentScan({ /* "com.ecm", */"com.ecm.core.dao","com.ecm.core.db","com.ecm.core.entity","com.ecm.core.bpm","com.ecm.core.service","com.ecm.core.cache.*","com.ecm.core.util","com.ecm.portal.*","com.ecm.flowable"/*,"org.flowable.ui.modeler","org.flowable.ui.common"*/})
@MapperScan("com.ecm.core.dao")
@EnableTransactionManagement//(proxyTargetClass = true)
public class ZisECMApplication extends SpringBootServletInitializer {

	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ZisECMApplication.class);
    }

    public static void main(String[] args) throws Exception {
        ApplicationContext app= SpringApplication.run(ZisECMApplication.class, args);
        //SpringUtil.setAppcxt(app);
    }
    /*
    @Bean
    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
	    return new EmbeddedServletContainerCustomizer() {
		    @Override
		    public void customize(ConfigurableEmbeddedServletContainer container) {
		    //设置session超时时间为20分钟
		    container.setSessionTimeout(20, TimeUnit.MINUTES);
		    }
	    };
    }
    */
    
    @Bean
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    
//    @Bean
//    public FilterRegistrationBean loginFilterRegistration() {
//    
//      FilterRegistrationBean registration = new FilterRegistrationBean();
//      registration.setFilter(new LoginFilter());
//      registration.addUrlPatterns(env.getProperty("web.filter.pattern"));
//      //registration.addInitParameter("paramName", "paramValue");
//      //registration.setName("testFilter");
//      //registration.setOrder(1);
//      return registration;
//    }
	
	@Autowired
	private Environment env;

	//destroy-method="close"的作用是当数据库连接不使用的时候,就把该连接重新放到数据池中,方便下次使用调用.
	@Bean(destroyMethod =  "close")
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(env.getProperty("spring.datasource.url"));
		dataSource.setUsername(env.getProperty("spring.datasource.username"));//用户名
		dataSource.setPassword(env.getProperty("spring.datasource.password"));//密码
		dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		dataSource.setInitialSize(2);//初始化时建立物理连接的个数
		dataSource.setMaxActive(20);//最大连接池数量
		dataSource.setMinIdle(0);//最小连接池数量
		dataSource.setMaxWait(60000);//获取连接时最大等待时间，单位毫秒。
		dataSource.setValidationQuery(env.getProperty("spring.datasource.validationQuery"));//用来检测连接是否有效的sql
		dataSource.setTestOnBorrow(false);//申请连接时执行validationQuery检测连接是否有效
		dataSource.setTestWhileIdle(true);//建议配置为true，不影响性能，并且保证安全性。
		dataSource.setPoolPreparedStatements(false);//是否缓存preparedStatement，也就是PSCache
		return dataSource;
	}

}
