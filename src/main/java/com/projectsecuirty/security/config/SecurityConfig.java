package com.projectsecuirty.security.config;

import com.projectsecuirty.security.service.UserService;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1) PasswordEncoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2) DaoAuthenticationProvider bean wires in your UserService + PasswordEncoder
    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            UserService userService,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }

    // 3) SecurityFilterChain bean that uses the auth provider
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            DaoAuthenticationProvider authProvider
    ) throws Exception {
        http
          .authenticationProvider(authProvider)      // plug in our DAO provider
          .authorizeHttpRequests(auth -> auth
              .requestMatchers("/register", "/do_register", "/css/**").permitAll()
              .requestMatchers("/users").hasRole("ADMIN")
              .anyRequest().authenticated()
          )
          .formLogin(form -> form
        		    .loginPage("/login")
        		    .defaultSuccessUrl("/home", true)
        		    .permitAll()
        		)

          .logout(logout -> logout
              .logoutSuccessUrl("/login?logout")
              .permitAll()
          );
        return http.build();
    }
}
