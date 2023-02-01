package com.sct.rest.api.model.entity;

import com.sct.rest.api.model.entity.enums.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_customer")
    @SequenceGenerator(name = "seq_customer", sequenceName = "seq_customer", initialValue = 2, allocationSize = 1)
    private Long id;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "BALANCE")
    private Long balance;
    
    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;
}
