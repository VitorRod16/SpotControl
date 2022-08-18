package com.api.parkingcontrol.service;

import com.api.parkingcontrol.model.ParkingSpotModel;
import com.api.parkingcontrol.repository.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class ParkingSpotService {

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @Transactional
    public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) throws Exception {

        var park = parkingSpotRepository.findByNumber(parkingSpotModel.getNumber());

        if(park.isPresent()){
            throw new Exception("Vaga já existe");
        }

        return parkingSpotRepository.save(parkingSpotModel);
    }

    public List<ParkingSpotModel> getAll() {
        return parkingSpotRepository.findAll();
    }

    public ParkingSpotModel getById(UUID id) throws ChangeSetPersister.NotFoundException {
        return parkingSpotRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public boolean existsByPlateCar(String licensePlateCar) {
        return false;
    }

    public boolean existsBySpotNumber(String parkingSpotNumber) {
        return false;
    }
}