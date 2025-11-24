package com.exchange.stock.market.stockMarketServices.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "authorities")
public class Authorities {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "authorities_generator")
    @SequenceGenerator(name = "authorities_generator",
            sequenceName = "authorities_sequence",
            allocationSize = 1)
    @Column(name = "authorities_id")
    private int authoritiesid;


    @Column(name = "name")
    private  String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id")
    private Users users;

}
