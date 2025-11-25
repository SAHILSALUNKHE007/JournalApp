package com.sahilsalunkhe.journalApp.config;

import com.sahilsalunkhe.journalApp.customException.CustomAccessDeniedHandler;
import com.sahilsalunkhe.journalApp.services.UserDetailServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Profile("prod")
public class SpringSecurityP {

    // 1️⃣ Expose UserDetailsService as a BEAN
    @Bean
    public UserDetailServiceImp userDetailsService() {
        return new UserDetailServiceImp();
    }

    // 2️⃣ Password Encoder Bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 3️⃣ Authentication Provider Bean
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // 4️⃣ Security Filter Chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth

                        .anyRequest().authenticated()
                )
                .httpBasic(basic -> {})
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .exceptionHandling(ex->ex.accessDeniedHandler(customAccessDeniedHandler()));

        return http.build();
    }

    // 5️⃣ AuthenticationManager (needed for login)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler(){
        return  new CustomAccessDeniedHandler();
    }
}
