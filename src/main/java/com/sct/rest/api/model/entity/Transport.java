package com.sct.rest.api.model.entity;

import com.sct.rest.api.model.entity.enums.Condition;
import com.sct.rest.api.model.entity.enums.TransportStatus;
import com.sct.rest.api.model.entity.enums.TransportType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "Transport")
@Table(name = "TRANSPORT")
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_transport")
    @SequenceGenerator(name = "seq_transport", sequenceName = "seq_transport", initialValue = 5, allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_PARKING")
    private Parking parking;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private TransportType type;

    @Column(name = "IDENTIFICATION_NUMBER")
    private String identificationNumber;

    @Column(name = "COORDINATES")
    private String coordinates;

    @Column(name = "CONDITION")
    @Enumerated(EnumType.STRING)
    private Condition condition;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private TransportStatus status;

    @Column(name = "CHARGE_PERCENTAGE")
    private Long chargePercentage;

    @Column(name = "MAX_SPEED")
    private Long maxSpeed;
}
