package com.api.parkingcontrol.repository;

import com.api.parkingcontrol.model.ParkingSpotModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpotModel, UUID> {
    boolean existsByPlateCar(String plateCar);

    boolean existsByNumber(String number);

    boolean existsByApartmentAndBlock(String apartment, String block);

}
