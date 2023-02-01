package com.sct.rest.api.model.entity;

import com.sct.rest.api.model.entity.enums.ParkingType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity(name = "Parking")
@Table(name = "PARKING")
public class Parking {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_parking")
    @SequenceGenerator(name = "seq_parking", sequenceName = "seq_parking", initialValue = 3, allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "COORDINATES")
    private String coordinates;

    @Column(name = "ALLOWED_RADIUS")
    private Long allowedRadius;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private ParkingType type;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_PARKING")
    private List<Transport> transports = new ArrayList<>();

}
