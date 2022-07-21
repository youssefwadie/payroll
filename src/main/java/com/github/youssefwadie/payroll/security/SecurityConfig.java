package com.github.youssefwadie.payroll.security;

import com.github.youssefwadie.payroll.security.filters.JWTGeneratorFilter;
import com.github.youssefwadie.payroll.security.filters.JWTValidatorFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@AllArgsConstructor
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.cors().configurationSource(request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            //  TODO: set the allowed origins
            configuration.setAllowedHeaders(Collections.singletonList("*"));
            configuration.setAllowCredentials(true);
            return configuration;
        });

        // ==========================================================================================
        // this section can be done automatically, i.e,
        // spring security can pick the right UserDetailsService and PasswordEncoder to do the authentication,
        // but I decided to do it explicitly
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        http.authenticationProvider(authenticationProvider);
        // ==========================================================================================

        http.authorizeRequests(request -> {
            request.antMatchers(HttpMethod.GET, "/api/v1/employees/**", "/api/v1/departments/**").hasAnyAuthority("Admin", "User");
            request.antMatchers("/api/v1/**").hasAuthority("Admin");
            request.anyRequest().authenticated();
        });

        http.addFilterBefore(jwtValidatorFilter(), BasicAuthenticationFilter.class);
        http.addFilterAfter(jwtGeneratorFilter(), BasicAuthenticationFilter.class);
        http.httpBasic();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


    @Bean
    public JWTValidatorFilter jwtValidatorFilter() {
        return new JWTValidatorFilter();
    }


    @Bean
    public JWTGeneratorFilter jwtGeneratorFilter() {
        return new JWTGeneratorFilter();
    }
}
