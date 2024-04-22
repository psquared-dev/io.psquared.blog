package io.psquared.blog.config;

import io.psquared.blog.entity.User;
import io.psquared.blog.filter.JWTTokenGeneratorFilter;
import io.psquared.blog.filter.JWTTokenValidatorFilter;
import io.psquared.blog.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.Optional;

@Configuration
public class ProjectConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return username -> {
            return userRepository.
                    findByUsername(username).
                    orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(
                auth -> auth.anyRequest().authenticated()
        );

//        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());

        http.sessionManagement(session -> {
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        http.addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class);
        http.addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AppContext appContext(){
        return new AppContext();
    }
}
