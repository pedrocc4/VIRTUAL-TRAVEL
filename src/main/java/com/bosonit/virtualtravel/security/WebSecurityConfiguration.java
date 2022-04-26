package com.bosonit.virtualtravel.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // Inicializamos roles
        String[] rol = new String[2];
        rol[0] = "USUARIO";
        rol[1] = "ADMIN";

//        http
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Informacion: un usuario normal puede crear y visualizar reservas
        // los autobuses los gestiona el administrador
        http
                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/login").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/v0/reserva")
//                .permitAll()
//                //.hasAnyRole(rol[0], rol[1])
//                .antMatchers(HttpMethod.GET, "/api/v0/reserva")
//                .permitAll()
//                //.hasAnyRole(rol[0], rol[1])
//                .antMatchers(HttpMethod.GET, "/api/v0/reserva/{id}").hasAnyRole(rol[0], rol[1])
//                .antMatchers(HttpMethod.GET, "/api/v0/disponible/{ciudad}")
//                .permitAll()
//                //.hasAnyRole(rol[0], rol[1])
//                .antMatchers(HttpMethod.GET, "/autobuses").hasRole(rol[1])
//                .antMatchers("/", "/public/**", "/resources/public/**", "/resources/**")
//                .permitAll()
        //.anyRequest().authenticated()
        ;

    }
}