package com.exchange.stock.market.stockMarketServices.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "USERS_DETAILS")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "userDetails_generator")
    @SequenceGenerator(name = "userDetails_generator",
            sequenceName = "userDetails_sequence",
            allocationSize = 1)
    @Column(name = "user_details_id")
    private int userDetailsId;

    @Column(name = "cell_no")
    private String CellNo;

    @Column(name = "address")
    private  String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private  String state;

    @Column(name = "pin_code")
    private int pinCode;


    @Column(name = "amount_in_wallet")
    private double amountInWallet;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;
}
