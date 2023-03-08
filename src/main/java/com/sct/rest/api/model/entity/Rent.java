package com.sct.rest.api.model.entity;

import com.sct.rest.api.model.enums.RentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_rent")
    @SequenceGenerator(name = "seq_rent", sequenceName = "seq_rent", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "id_transport")
    private Transport transport;

    @Column(name = "begin_time_rent")
    private Timestamp beginTimeRent;

    @Column(name = "end_time_rent")
    private Timestamp endTimeRent;

    @ManyToOne
    @JoinColumn(name = "id_begin_parking")
    private Parking beginParking;

    @ManyToOne
    @JoinColumn(name = "id_end_parking")
    private Parking endParking;

    @Enumerated(EnumType.STRING)
    private RentStatus status;

    private Long amount;
}
