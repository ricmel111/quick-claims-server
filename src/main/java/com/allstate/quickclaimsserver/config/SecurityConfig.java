package com.allstate.quickclaimsserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity //tells application it needs to require header/authentication and how to secure url
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    //AUTHENTICATION
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    //AUTHORIZATION
    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http.cors().and().authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll(); //allow browser to check everything first. ANy htttp request using OPTION will be allowed through

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/claim/**")
                .hasAnyRole("USER","MANAGER")
                .antMatchers(HttpMethod.POST, "/api/login")
                .hasAnyRole("USER","MANAGER")
                .antMatchers(HttpMethod.POST, "/api/claim/**")
                .hasAnyRole("MANAGER")
                .and().csrf().disable() //cross site request forgery...doesn't apply to rest, only security issue if creating web pages directly in Spring
                .httpBasic();


    }


}
