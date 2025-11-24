package com.exchange.stock.market.stockMarketServices.service;


import com.exchange.stock.market.stockMarketServices.Repository.UserRepository;
import com.exchange.stock.market.stockMarketServices.models.Users;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;


import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("The email received for authentication ---{}", email);
        Users users = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Cannot find the details for the user" + email));
        String authorities = users.getAuthorities().stream()
                .map(authorities1 -> authorities1.getName()).collect(Collectors.joining(","));
        log.info("The authorities that are provided to the users  are  ---{}", authorities);
        UserDetails userDetails = User
                .withUsername(users.getUserName())
                .password(users.getPassword())
                .authorities(authorities).build();
        log.info("The User Details for the application {} -- is authenticated",
                users.getUserId(), users.getUserAutorities());
        return userDetails;
    }
}
