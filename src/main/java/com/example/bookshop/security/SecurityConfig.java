package com.example.bookshop.security;

import com.example.bookshop.filter.CustomAuthenticationFilter;
import com.example.bookshop.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

import static org.springframework.http.HttpMethod.GET;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");
        http.cors().configurationSource(request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
            corsConfiguration.setAllowedOrigins(Arrays.asList("https://book-shop-web.herokuapp.com/"));
            corsConfiguration.setAllowedMethods(Arrays.asList("*"));
            return corsConfiguration;
        });
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers("/api/v1/login/**", "/api/v1/users/", "/api/v1/token/refresh/**")
                .permitAll();
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/v1/**")
                .permitAll();
        http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE, "/apu/v1/orders/**")
                .hasAnyAuthority("USER", "ADMIN");
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/comments/", "/api/v1/orders/")
                .hasAnyAuthority("USER", "ADMIN");
        http.authorizeRequests()
                .antMatchers(
                        HttpMethod.DELETE, "/api/v1/comments/**", "/api/v1/books/**",
                        "/api/v1/categories/**", "/api/v1/users/**")
                .hasAnyAuthority("ADMIN");
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/**")
                .hasAnyAuthority("ADMIN");
        http.authorizeRequests()
                .antMatchers(HttpMethod.PUT, "/api/v1/**")
                .hasAnyAuthority("ADMIN");
        http.authorizeRequests()
                .antMatchers(HttpMethod.PATCH, "/api/v1/**")
                .hasAnyAuthority("USER", "ADMIN");
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
