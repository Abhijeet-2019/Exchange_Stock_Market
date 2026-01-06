package com.exchange.stock.market.portfolio.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.security.*;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

//@Configuration
public class SwaggerConfig {

//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .servers(List.of(
//                        new Server()
//                                .url("http://localhost:8080/portfolio-service")
//                                .description("API Gateway")
//                ))
//                .components(new Components()
//                        .addSecuritySchemes("oauth2", new SecurityScheme()
//                                .type(SecurityScheme.Type.OAUTH2)
//                                .flows(new OAuthFlows()
//                                        .authorizationCode(new OAuthFlow()
//                                                .authorizationUrl(
//                                                        "http://localhost:8180/realms/Stock_Exchange_API/protocol/openid-connect/auth"
//                                                )
//                                                .tokenUrl(
//                                                        "http://localhost:8180/realms/Stock_Exchange_API/protocol/openid-connect/token"
//                                                )
//                                                .scopes(new Scopes()
//                                                        .addString("openid", "OpenID scope")
//                                                        .addString("profile", "Profile scope")
//                                                        .addString("roles", "Roles")
//                                                        .addString("openid", "openid")
//                                                )
//                                        )
//                                )
//                        )
//                )
//                .addSecurityItem(new SecurityRequirement().addList("oauth2"));
//    }
}
