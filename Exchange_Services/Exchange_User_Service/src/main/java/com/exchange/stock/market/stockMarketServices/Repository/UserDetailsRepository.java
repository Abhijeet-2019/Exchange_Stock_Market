package com.exchange.stock.market.stockMarketServices.Repository;


import com.exchange.stock.market.stockMarketServices.models.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails,Long> {
}
