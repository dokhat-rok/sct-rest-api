package com.sct.rest.api.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "route_point")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RoutePointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_route_point")
    @SequenceGenerator(name = "seq_route_point", sequenceName = "seq_route_point", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_rent")
    private RentEntity rent;

    private Double latitude;

    private Double longitude;

    @Column(name = "created_date")
    private ZonedDateTime created_date;
}
