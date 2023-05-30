package com.company.whatcapp.config;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;


import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class AppConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests(auth-> {
                     auth.requestMatchers(HttpMethod.POST,"/api/v1/auth/signup").permitAll();
                     auth.requestMatchers(HttpMethod.POST,"/api/v1/auth/login").permitAll();
                     auth.anyRequest().permitAll();
                 })
                .addFilterAfter(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf().disable()
                .cors().configurationSource(new CorsConfigurationSource() {
                     @Override
                     public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                         CorsConfiguration corsConfiguration = new CorsConfiguration();
                         corsConfiguration.addAllowedOrigin(Arrays.asList("http://localhost:3000").toString());
                         corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                         corsConfiguration.setAllowCredentials(true);
                         corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                         corsConfiguration.setExposedHeaders(Arrays.asList("Authorization"));
                         corsConfiguration.setMaxAge(3600L);
                         return corsConfiguration;
                     }})
                 .and().formLogin().and().httpBasic();

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
