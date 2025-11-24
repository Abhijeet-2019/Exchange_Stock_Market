package com.exchange.stock.market.stockMarketServices.Controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = "http://localhost:8080") // Allow requests from port 8080
public class PublicController {

    @GetMapping("/test")
    @Operation(summary = "This is a test APL    ")
    @Tag(name = "Test API")
    public ResponseEntity testPublicAPI
            (@RequestParam(name = "testAPI", required = true)
             @Parameter(example = "Test") String userNane) {
        log.info("This is a Test API for Public, {}", userNane);
        return new ResponseEntity<String>("Hi i am working ", null, 200);
    }
}