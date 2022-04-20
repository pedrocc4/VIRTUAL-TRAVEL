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
        // Informacion: un usuario normal puede crear y visualizar reservas
        // los autobuses los gestiona el administrador
        http
                //.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v0/reserva").hasAnyRole("USUARIO", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v0/reserva").hasAnyRole("USUARIO", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v0/reserva/{id}").hasAnyRole("USUARIO", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v0/disponible/{ciudad}").hasAnyRole("USUARIO", "ADMIN")
                .antMatchers(HttpMethod.GET, "/autobuses").hasRole("ADMIN")
                .antMatchers("/", "/public/**", "/resources/public/**", "/resources/**")
                .permitAll()
                .anyRequest().authenticated();
    }
}