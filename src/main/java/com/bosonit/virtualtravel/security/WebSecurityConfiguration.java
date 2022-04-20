package com.bosonit.virtualtravel.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v0/reserva").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v0/reserva").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v0/reserva/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v0/disponible/{ciudad}").permitAll()
                .antMatchers(HttpMethod.GET, "/autobuses").permitAll()
                .antMatchers("/", "/public/**", "/resources/**", "/resources/public/**")
                .permitAll()
                .anyRequest().authenticated();
    }
}