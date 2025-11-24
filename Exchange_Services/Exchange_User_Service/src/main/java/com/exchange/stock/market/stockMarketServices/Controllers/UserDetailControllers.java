package com.exchange.stock.market.stockMarketServices.Controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/userDetails")
@CrossOrigin(origins = "http://localhost:8080") // Allow requests from port 8080
public class UserDetailControllers {


    @GetMapping("/fetchUserDetails")
    @Operation(summary = "Fetch  User Details")
    @Tag(name = "User Details")
    public ResponseEntity fetchUserDetails
            (@RequestParam(name = "stockName", required = true)
             @Parameter(example = "Reliance") String userNane) {
        log.info("Fetch all the details of the user, {}", userNane);
        return new ResponseEntity<String>("Hi i am working ", null, 200);
    }
}
