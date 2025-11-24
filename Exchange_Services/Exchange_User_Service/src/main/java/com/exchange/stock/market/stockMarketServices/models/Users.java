package com.exchange.stock.market.stockMarketServices.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.* ;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "user_generator")
    @SequenceGenerator(name = "user_generator",
            sequenceName = "user_sequence",
            allocationSize = 1)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "last_login_time")
    private Timestamp lastLoginTime;

    @Column(name = "create_time")
    private Timestamp createTime;


    @Transient
    private String userAutorities;

    @OneToOne(mappedBy = "users", fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private UserDetails userDetails;


    @OneToMany(mappedBy = "users", fetch = FetchType.EAGER)
    @JsonIgnore
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
        private Set<Authorities> authorities;

    public void addAuthorities(Set<Authorities> authorities)
    {
        authorities.forEach(authorities1 -> authorities1.setUsers(this));
        this.setAuthorities(authorities);
    }

    public void addUserDetails(UserDetails userDetails){
        userDetails.setUsers(this);
        this.setUserDetails(userDetails);
    }
}
