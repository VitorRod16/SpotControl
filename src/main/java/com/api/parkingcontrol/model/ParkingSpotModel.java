package com.api.parkingcontrol.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "TB_PARKING_SPOT")
public class ParkingSpotModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, length = 10)
    private String number;

    @Column(unique = true, length = 7)
    private String plateCar;

    @Column(length = 70)
    private String brandCar;

    @Column(length = 70)
    private String modelCar;

    @Column(length = 70)
    private String colorCar;

    @Column
    private LocalDateTime registrationDate;

    @Column(length = 130)
    private String owner;

    @Column(length = 30)
    private String apartment;

    @Column(length = 30)
    private String block;
}
