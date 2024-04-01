package com.dael.ApiRest.config;

import com.dael.ApiRest.config.filter.JwtTokenValidator;
import com.dael.ApiRest.service.impl.UserDetailServiceImpl;
import com.dael.ApiRest.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private JwtUtils jwtUtils;

    @Bean
    //Aqui se crean nuestras condiciones personalizadas
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())//se desabilita pq no se necesita
                .httpBasic(Customizer.withDefaults())//se utiliza solo para logearse con usuario y contraseña
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    // Configurar los endpoints publicos
                    http.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();
//
                    // Configurar los endpoints privados

                    //Endpoint Maker
                    http.requestMatchers(HttpMethod.POST, "/api/maker/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.GET, "/api/maker/**").hasAnyRole("ADMIN","USER");
                    http.requestMatchers(HttpMethod.PUT, "/api/maker/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "/api/maker/**").hasAnyRole("ADMIN");

                    //Endpoint Product
                    http.requestMatchers(HttpMethod.GET, "/api/product/**").hasAnyRole("ADMIN", "USER", "INVITED");
                    http.requestMatchers(HttpMethod.POST, "/api/product/**").hasAnyRole("ADMIN", "USER");
                    http.requestMatchers(HttpMethod.PUT, "/api/product/**").hasAnyRole("ADMIN", "USER");
                    http.requestMatchers(HttpMethod.DELETE, "/api/product/**").hasAnyRole("ADMIN", "USER");

//
                    // Configurar el resto de endpoint - NO ESPECIFICADOS
                    http.anyRequest().denyAll();//cualquier endpoint no especificado(publicos, privados) lo va denegar
//                    http.anyRequest().authenticated();//cualquier endpoint no especificado(publicos, privados) mientras este autenticado va pasar
                })
                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //UserDetailServiceImpl userDetailService
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService((userDetailService));
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();//solo se usa para pruebas
        return new BCryptPasswordEncoder();//producion
    }
}