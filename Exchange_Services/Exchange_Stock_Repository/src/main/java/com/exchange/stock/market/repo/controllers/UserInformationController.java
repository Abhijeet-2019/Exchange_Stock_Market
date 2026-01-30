package com.exchange.stock.market.repo.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j

@AllArgsConstructor
@Tag(name = "User Information")
@CrossOrigin(origins = "http://localhost:8080") // Allow requests from port 8080
public class UserInformationController {

    @GetMapping("/fetchUserDetails")
    @Operation(summary = "Fetch all User Details Information")
    public ResponseEntity<Object> fetchUserDetails(Authentication authentication){
        Jwt jwt = (Jwt) authentication.getPrincipal();

        String username = jwt.getClaimAsString("preferred_username");
        String email = jwt.getClaimAsString("email");
        String userId = jwt.getSubject();
        return ResponseEntity.ok("User: " + username);
    }
}
