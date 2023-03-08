package com.sct.rest.api.model.entity;

import com.sct.rest.api.model.enums.ParkingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_parking")
    @SequenceGenerator(name = "seq_parking", sequenceName = "seq_parking", initialValue = 3, allocationSize = 1)
    private Long id;

    private String name;

    private String coordinates;

    @Column(name = "allowed_radius")
    private Long allowedRadius;

    @Enumerated(EnumType.STRING)
    private ParkingType type;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_parking")
    private List<Transport> transports = new ArrayList<>();

}
