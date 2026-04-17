package com.exchange.stock.market.stockMarketServices.Controllers;


import com.exchange.stock.market.stockMarketServices.service.MyUserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/userDetails")
public class UserDetailControllers {

    private MyUserDetailsService myUserDetailsService;

    UserDetailControllers(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @GetMapping("/fetchUserDetails")
    @Operation(summary = "Fetch User Details for a user")
    @Tag(name = "User Details")
    public ResponseEntity fetchUserDetails
            (@RequestParam(name = "userName", required = true)
             @Parameter(example = "email@domainName") String userNane) {
        log.info("Fetch all the details of the user, {}", userNane);
        UserDetails userDetails = null;
        try {
            userDetails=  myUserDetailsService.loadUserByUsername(userNane);
        } catch (Exception ex) {
            log.error("Unable to load user details for email  {}", userNane);
            return new ResponseEntity("Unable to fetch user deatils ", null, 500);
        }
        return new ResponseEntity<String>("Hi i am working " + userDetails, null, 200);
    }
}
