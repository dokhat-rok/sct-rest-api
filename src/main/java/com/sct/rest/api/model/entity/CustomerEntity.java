package com.sct.rest.api.model.entity;

import com.sct.rest.api.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "customer")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_customer")
    @SequenceGenerator(name = "seq_customer", sequenceName = "seq_customer", initialValue = 2, allocationSize = 1)
    private Long id;

    private String login;

    private String password;

    private Long balance;

    @Enumerated(EnumType.STRING)
    private Role role;
}
