package com.exchange.stock.market.apiGateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Flux;


@Configuration
@EnableWebFluxSecurity
public class ApiSecurityConfig {

    @Bean
   @Order(1)
    public SecurityWebFilterChain security(ServerHttpSecurity http) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(ex -> ex
                        // Swagger
                        .pathMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/oauth2-redirect.html"
                        ).permitAll()
                        // OAuth2 endpoints
                        .pathMatchers(
                                "/oauth2/**",
                                "/login/oauth2/**"
                        ).permitAll()
                        // CORS preflight
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        .anyExchange().authenticated()
                )
                // Enables Swagger Authorize button
                .oauth2Login(Customizer.withDefaults())
                // Resource server (JWT)
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(reactiveJwtAuthenticationConverter())
                        )
                )
                .build();
    }


    /**
     * This is used for react application for login
     * @param http
     * @return
     */

    @Bean
    @Order(2)
    public SecurityWebFilterChain apiChain(ServerHttpSecurity http) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(ex -> ex
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(reactiveJwtAuthenticationConverter())
                        )
                )
                .build();
    }

    @Bean
    public ReactiveJwtAuthenticationConverter reactiveJwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter =
                new JwtGrantedAuthoritiesConverter();
        // Keycloak roles claim
        authoritiesConverter.setAuthorityPrefix("ROLE_");
        authoritiesConverter.setAuthoritiesClaimName("roles");
        ReactiveJwtAuthenticationConverter converter =
                new ReactiveJwtAuthenticationConverter();

        // THIS is the correct line
        converter.setJwtGrantedAuthoritiesConverter(
                jwt -> Flux.fromIterable(authoritiesConverter.convert(jwt))
        );

        return converter;
    }

}
