package com.example.SocialApp.config;

import com.example.SocialApp.security.JwtAuthenticationEntryPoint;
import com.example.SocialApp.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // ✅ Disable CSRF (stateless JWT app)
                .csrf(AbstractHttpConfigurer::disable)

                // ✅ Stateless session (no HttpSession)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // ✅ JWT error handling (401 Unauthorized)
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )

                // ✅ Route permissions
                .authorizeHttpRequests(auth -> auth

                        // 🔓 Public — Auth routes
                        .requestMatchers(HttpMethod.POST, "/auth/register", "/auth/login").permitAll()

                        // 🔓 Public — Serve uploaded images directly
                        .requestMatchers("/uploads/**").permitAll()

                        // 🔓 Public — User profile image access
                        .requestMatchers("/user/image/**").permitAll()

                        // 🔓 Public — GET posts (optional, remove if you want auth)
                        .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll()

                        // 🔒 Image upload — JWT required
                        .requestMatchers(HttpMethod.POST, "/api/images/upload").authenticated()

                        // 🔒 Everything else — JWT required
                        .anyRequest().authenticated()
                )

                // ✅ Disable default form login & basic auth
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                // ✅ Add JWT filter before Spring's default auth filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ✅ Password encoder — BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ AuthenticationManager — needed for login in AuthService
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}