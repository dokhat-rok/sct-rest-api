package com.sct.rest.api.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "User")
@Table(name = "customer")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
    @SequenceGenerator(name = "seq_user", sequenceName = "seq_user", initialValue = 2, allocationSize = 1)
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
