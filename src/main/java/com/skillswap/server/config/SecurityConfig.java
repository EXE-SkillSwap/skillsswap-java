package com.skillswap.server.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${cors.client.url}")
    private String allowedOrigins;

    private final AuthDecode authDecode;
    private final AuthEntryPoint authEntryPoint;
    private static final List<Map.Entry<String, HttpMethod>> SECURED_URLS = List.of(
            Map.entry("/api/users/p", HttpMethod.GET),
            Map.entry("/api/users/skill-tags", HttpMethod.POST),
            Map.entry("/api/memberships", HttpMethod.POST),
            Map.entry("/api/memberships", HttpMethod.GET),
            Map.entry("/api/memberships/payment/**", HttpMethod.POST),
            Map.entry("/api/memberships/my", HttpMethod.GET),
            Map.entry("/api/users/update-profile", HttpMethod.PUT),
            Map.entry("/api/users/find-friends", HttpMethod.GET),
            Map.entry("/api/users/upload-profile-images", HttpMethod.POST),
            Map.entry("/api/chat/create/new-chat/{userId}", HttpMethod.POST),
            Map.entry("/api/chat/current/conversations", HttpMethod.GET),
            Map.entry("/api/chat/messages/conversation/{conservationId}", HttpMethod.GET),
            Map.entry("/api/notifications", HttpMethod.GET),
            Map.entry("/api/notifications/make-read/{notificationId}", HttpMethod.PUT),
            Map.entry("/api/notifications/make-all-read", HttpMethod.PUT),
            Map.entry("/api/courses", HttpMethod.POST),
            Map.entry("/api/courses/my-courses", HttpMethod.GET)
    );

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> {
                    SECURED_URLS.forEach(entry -> authorize.requestMatchers(entry.getValue(), entry.getKey()).authenticated()
                    );
                    authorize.anyRequest().permitAll();
                });

        http.oauth2ResourceServer(resourceServer ->
                resourceServer
                        .jwt(jwtConfigurer -> jwtConfigurer.decoder(authDecode)
                                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                        .authenticationEntryPoint(authEntryPoint));

        http.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedOrigins(List.of(allowedOrigins.split(",")));
            corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
            corsConfiguration.setAllowedHeaders(List.of("*"));
            corsConfiguration.setAllowCredentials(true);
            return corsConfiguration;
        }));
        return http.build();
    }

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
