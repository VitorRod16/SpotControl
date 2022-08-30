package com.api.parkingcontrol.service;

import com.api.parkingcontrol.model.ParkingSpotModel;
import com.api.parkingcontrol.repository.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingSpotService {

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @Transactional
    public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
        return parkingSpotRepository.save(parkingSpotModel);
    }

    public Page<ParkingSpotModel> getAll(Pageable pageable) {
        return parkingSpotRepository.findAll(pageable);
    }

    public Optional<ParkingSpotModel> getById(UUID id) {
        return parkingSpotRepository.findById(id);
    }

    public boolean existsByPlateCar(String plateCar) {
        return parkingSpotRepository.existsByPlateCar(plateCar);
    }

    public boolean existsByNumber(String number) {
        return parkingSpotRepository.existsByNumber(number);
    }

    public boolean existsByApartmentAndBlock(String apartment, String block) {
        return parkingSpotRepository.existsByApartmentAndBlock(apartment, block);
    }

    @Transactional
    public void deleteById(UUID id) {
        parkingSpotRepository.deleteById(id);
    }
}