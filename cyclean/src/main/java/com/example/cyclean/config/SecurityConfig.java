package com.example.cyclean.config;

import com.example.cyclean.config.jwt.JwtAuthenticationFilter;
import com.example.cyclean.config.jwt.JwtAuthorizationFilter;
import com.example.cyclean.config.jwt.JwtProcessor;
import com.example.cyclean.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;
    private final JwtProcessor jwtProcessor;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(), jwtProcessor);
        jwtAuthenticationFilter.setFilterProcessesUrl("/account/login");
        http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable();
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .addFilter(corsFilter())
                .addFilter(jwtAuthenticationFilter)
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtProcessor))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository, jwtProcessor));
        http
                .authorizeRequests()
                .mvcMatchers("/account/**").permitAll()
                .mvcMatchers("/board/**").permitAll()
                .mvcMatchers("/reply/**").permitAll()//** 홈페이지, login, register
                .anyRequest().hasAuthority("USER");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }
}