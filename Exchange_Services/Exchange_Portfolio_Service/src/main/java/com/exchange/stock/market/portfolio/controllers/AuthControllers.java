package com.exchange.stock.market.portfolio.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/fetchOAuthDetails")
@CrossOrigin(origins = "http://localhost:8080")
public class AuthControllers {

    @Autowired
    OAuth2AuthorizedClientService oauth2ClientService;

    @GetMapping("/fetchAll")
    public String fetchAll(@AuthenticationPrincipal OidcUser principal,
                           OAuth2AuthenticationToken oauthToken) {
        OAuth2AuthorizedClient client =
                oauth2ClientService.loadAuthorizedClient(
                        oauthToken.getAuthorizedClientRegistrationId(),
                        oauthToken.getName()
                );
        String accessToken = client.getAccessToken().getTokenValue();
        String idToken = principal.getIdToken().getTokenValue();
        return " Hi My Access token is " +
                "AccessToken = " + accessToken + "\nID Token = " + idToken;
    }
}
