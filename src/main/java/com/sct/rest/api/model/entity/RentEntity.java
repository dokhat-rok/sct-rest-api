package com.sct.rest.api.model.entity;

import com.sct.rest.api.model.enums.RentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rent")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_rent")
    @SequenceGenerator(name = "seq_rent", sequenceName = "seq_rent", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "id_transport")
    private TransportEntity transport;

    @Column(name = "begin_time_rent")
    private ZonedDateTime beginTimeRent;

    @Column(name = "end_time_rent")
    private ZonedDateTime endTimeRent;

    @ManyToOne
    @JoinColumn(name = "id_begin_parking")
    private ParkingEntity beginParking;

    @ManyToOne
    @JoinColumn(name = "id_end_parking")
    private ParkingEntity endParking;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rent")
    private List<RoutePointEntity> routePoints = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private RentStatus status;

    private Long amount;
}
