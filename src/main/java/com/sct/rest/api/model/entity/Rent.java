package com.sct.rest.api.model.entity;

import com.sct.rest.api.model.entity.enums.RentStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity(name = "Rent")
@Table(name = "RENT")
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_rent")
    @SequenceGenerator(name = "seq_rent", sequenceName = "seq_rent", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_CUSTOMER")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "ID_TRANSPORT")
    private Transport transport;

    @Column(name = "BEGIN_TIME_RENT")
    private Timestamp beginTimeRent;

    @Column(name = "END_TIME_RENT")
    private Timestamp endTimeRent;

    @ManyToOne
    @JoinColumn(name = "ID_BEGIN_PARKING")
    private Parking beginParking;

    @ManyToOne
    @JoinColumn(name = "ID_END_PARKING")
    private Parking endParking;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private RentStatus status;

    @Column(name = "AMOUNT")
    private Long amount;
}
