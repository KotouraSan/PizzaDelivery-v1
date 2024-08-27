package uz.ksan.backend.pizzadelivery.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.ksan.backend.pizzadelivery.security.jwt.JwtFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(
                        "/api/v1/client/newClient",
                        "/api/v1/user/auth/**",
                        "/api/v1/auth/sign-in",
                        "/api/v1/auth/refresh",
                        "/api/v1/orders/**",
                        "/api/v1/pizza/"
                        ).permitAll()

                        .requestMatchers("/api/v1/client/displayAll",
                                        "/api/v1/pizza/newPizza").hasAuthority("ROLE_ADMIN")

                        .requestMatchers("/api/v1/client/profile",
                                        "/api/v1/orders/create",
                                        "/api/v1/orders/remove/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

                                .requestMatchers("/api/v1/client/**").authenticated())

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(4);
    }
}
