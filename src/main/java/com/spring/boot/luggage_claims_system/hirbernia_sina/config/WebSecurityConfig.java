package com.spring.boot.luggage_claims_system.hirbernia_sina.config;

import com.spring.boot.luggage_claims_system.hirbernia_sina.authentication.UserAuthenticationProvider;
import com.spring.boot.luggage_claims_system.hirbernia_sina.service.HSUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Liu Dairui
 * @date 2019-04-11 21:00
 */
@Configuration
@EnableWebSecurity //Open function of Spring Security
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserAuthenticationProvider authenticationProvider;
    @Autowired
    private HSUserDetailsService userDetailsService;

    /**
     * Define static resources that do not require filtering (equivalent to permitAll of HttpSecurity)
     */
    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/css/**", "/js/**", "/shop.html", "/about.html", "/contact.html");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    /*  保存用户信息到内存中
        auth.inMemoryAuthentication()
             .withUser("张三").password("123456").roles("VIP1")
             .and()
             .withUser("李四").password("123456").roles("VIP2");
    */

        /*自定义认证*/
        auth.authenticationProvider(authenticationProvider);
        auth.userDetailsService(userDetailsService);
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * Security strategy configuration
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/", "/index", "/register", "/result", "/claim/finish").permitAll()
                .antMatchers("/employee/**").hasAuthority("EMPLOYEE")
                // Authentication needs to be specified for some resources of the website
                //.antMatchers("/admin/**").hasRole("ADMIN")
                // All requests except the above require authentication
                .anyRequest().authenticated().and()
                // Define the login page to which a user needs to log in
                .formLogin().loginPage("/signin").usernameParameter("emailAddress")  //username
                .passwordParameter("password").defaultSuccessUrl("/employee/employee").failureUrl("/login-error").permitAll().and()
                // Define logout operation
                .logout().logoutSuccessUrl("/signin?logout").permitAll().and()
                .rememberMe().rememberMeParameter("remember").tokenValiditySeconds(604800).and()
                .csrf().disable()
        ;
        // disable cache
        httpSecurity.headers().cacheControl();
    }
}