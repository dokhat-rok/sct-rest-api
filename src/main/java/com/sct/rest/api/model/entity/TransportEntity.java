package com.sct.rest.api.model.entity;

import com.sct.rest.api.model.enums.Condition;
import com.sct.rest.api.model.enums.TransportStatus;
import com.sct.rest.api.model.enums.TransportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "transport")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TransportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_transport")
    @SequenceGenerator(name = "seq_transport", sequenceName = "seq_transport", initialValue = 5, allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_parking")
    private ParkingEntity parking;

    @Enumerated(EnumType.STRING)
    private TransportType type;

    @Column(name = "identification_number")
    private String identificationNumber;

    private String coordinates;

    @Enumerated(EnumType.STRING)
    private Condition condition;

    @Enumerated(EnumType.STRING)
    private TransportStatus status;

    @Column(name = "charge_percentage")
    private Long chargePercentage;

    @Column(name = "max_speed")
    private Long maxSpeed;
}
