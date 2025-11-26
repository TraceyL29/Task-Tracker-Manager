package com.taskTrackerManager.TaskTracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Security Configuration with CSRF Protection
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // CSRF Token Handler
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // <-- Use our custom CORS config
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(requestHandler)
                        .ignoringRequestMatchers("/api/auth/csrf-token")
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Arrays.asList("http://localhost:3000"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

/*
============================================
🔐 HOW CSRF PROTECTION WORKS:
============================================

1. LOGIN:
   - User logs in successfully
   - Backend creates session (httpOnly cookie)
   - Backend also creates CSRF token
   - CSRF token sent in cookie: XSRF-TOKEN=xyz789...
   - This cookie is NOT httpOnly (JavaScript can read it)

2. MAKING REQUESTS:
   - Frontend reads CSRF token from cookie
   - Adds token to request header: X-XSRF-TOKEN: xyz789
   - Browser also sends session cookie (automatic)
   - Backend checks BOTH:
     ✅ Session cookie valid?
     ✅ CSRF token matches?
     → Request allowed!

3. EVIL SITE ATTACK:
   - Evil site makes request
   - Browser sends session cookie ✅ (automatic)
   - But evil site CAN'T read CSRF token ❌ (CORS blocks)
   - Backend sees missing/wrong CSRF token
   - → Request BLOCKED! ✅

============================================
📝 WHY CSRF TOKEN ISN'T httpOnly:
============================================

- Session cookie: httpOnly ✅ (XSS protection)
  → JavaScript can't read it
  → Browser sends automatically
  → This is the AUTHENTICATION

- CSRF token: NOT httpOnly ✅ (needs to be read)
  → JavaScript CAN read it
  → Must be sent manually in header
  → This is the VALIDATION

Both needed together = Secure! 🔒

============================================
*/