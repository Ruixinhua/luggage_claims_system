package com.spring.boot.luggage_claims_system.hirbernia_sina.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Liu Dairui
 * @date 2019-04-11 21:00
 */
@Configuration
@EnableWebSecurity //Open function of Spring Security
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 定义不需要过滤的静态资源（等价于HttpSecurity的permitAll）
     */
    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/css/**");
    }

    /**
     * 安全策略配置
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/index").permitAll()
                // 对于网站部分资源需要指定鉴权
                //.antMatchers("/admin/**").hasRole("ADMIN")
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated().and()
                // 定义当需要用户登录时候，转到的登录页面
                .formLogin().loginPage("/employee/signin").defaultSuccessUrl("/index").permitAll().and()
                // 定义登出操作
                .logout().logoutSuccessUrl("/login?logout").permitAll().and()
                .csrf().disable()
        ;
        // 禁用缓存
        httpSecurity.headers().cacheControl();
    }
}