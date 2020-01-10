package org.zisecm.extinterface.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启方法权限控制
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder  auth) throws  Exception{
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
 
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                // 所有用户均可访问的资源
//                .antMatchers( "/favicon.ico","/css/**","/common/**","/js/**","/images/**","/captcha.jpg","/login","/userLogin","/login-error").permitAll()
//                // 任何尚未匹配的URL只需要验证用户即可访问
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().loginPage("/login").successForwardUrl("/index").failureForwardUrl("/login?error=1")
//                .and()
//                //权限拒绝的页面
//                .exceptionHandling().accessDeniedPage("/403");
// 
//        http.logout().logoutSuccessUrl("/login");
    	
    	 http.csrf().disable()//禁用了 csrf 功能
         .authorizeRequests()//限定签名成功的请求
         .antMatchers("/decision/**","/govern/**","/employee/*").hasAnyRole("EMPLOYEE","ADMIN")//对decision和govern 下的接口 需要 USER 或者 ADMIN 权限
         .antMatchers("/employee/login").permitAll()///employee/login 不限定
         .antMatchers("/admin/**").hasRole("ADMIN")//对admin下的接口 需要ADMIN权限
         .antMatchers("/oauth/**").permitAll()//不拦截 oauth 开放的资源
         .anyRequest().permitAll()//其他没有限定的请求，允许访问
         .and().anonymous()//对于没有配置权限的其他请求允许匿名访问
         .and().formLogin()//使用 spring security 默认登录页面
         .and()
         //权限拒绝的页面
         .exceptionHandling().accessDeniedPage("/403")
         .and().httpBasic();//启用http 基础验证
    	
    }
 
 
    /**
     * 设置用户密码的加密方式
     * @return
     */
    @Bean
    public Md5PasswordEncoder passwordEncoder() {
        return new Md5PasswordEncoder();
 
    }
 
    /**
     * 自定义UserDetailsService，授权
     * @return
     */
//    @Bean
//    public CustomUserDetailsService customUserDetailsService(){
//        return new CustomUserDetailsService();
//    }
 
    /**
     * AuthenticationManager
     * @return
     * @throws Exception
     */
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
 
}
