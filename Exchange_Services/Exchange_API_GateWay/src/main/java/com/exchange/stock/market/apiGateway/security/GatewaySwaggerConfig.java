package com.exchange.stock.market.apiGateway.security;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.customizers.OpenApiCustomizer;

@Configuration
public class GatewaySwaggerConfig {

    @Bean
    public OpenApiCustomizer securityOpenApiCustomiser() {
        return openApi -> {

            Components components = openApi.getComponents();
            if (components == null) {
                components = new Components();
                openApi.setComponents(components);
            }
            components.addSecuritySchemes("oauth2", new SecurityScheme()
                    .type(SecurityScheme.Type.OAUTH2)
                    .flows(new OAuthFlows()
                            .authorizationCode(new OAuthFlow()
                                    .authorizationUrl(
                                            "http://localhost:8180/realms/Stock_Exchange_API/protocol/openid-connect/auth"
                                    )
                                    .tokenUrl(
                                            "http://localhost:8180/realms/Stock_Exchange_API/protocol/openid-connect/token"
                                    )
                                    .scopes(new Scopes()
                                            .addString("openid", "OpenID")
                                            .addString("profile", "Profile")
                                    )
                            )
                    )
            );
            openApi.addSecurityItem(
                    new SecurityRequirement().addList("oauth2")
            );
        };
    }
}
