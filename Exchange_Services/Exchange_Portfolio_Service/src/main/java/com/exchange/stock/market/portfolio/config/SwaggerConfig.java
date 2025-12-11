package com.exchange.stock.market.portfolio.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("oauth2", new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .flows(new OAuthFlows()
                                        .authorizationCode(new OAuthFlow()
                                                .authorizationUrl("http://localhost:8180/realms/Stock_Exchange_API/protocol/openid-connect/auth")
                                                .tokenUrl("http://localhost:8180/realms/Stock_Exchange_API/protocol/openid-connect/token")
                                                .scopes(new Scopes()
                                                        .addString("openid", "OpenID scope")
                                                        .addString("profile", "Profile scope")
                                                        .addString("roles", "Roles"))
                                        ))))
                .addSecurityItem(new SecurityRequirement().addList("oauth2"));
    }
}
