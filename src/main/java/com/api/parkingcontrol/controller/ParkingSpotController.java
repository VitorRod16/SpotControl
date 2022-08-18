package com.api.parkingcontrol.controller;

import com.api.parkingcontrol.dto.ParkingSpotDTO;
import com.api.parkingcontrol.model.ParkingSpotModel;
import com.api.parkingcontrol.service.ParkingSpotService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/park")
public class ParkingSpotController {

    @Autowired
    private ParkingSpotService parkingSpotService;

    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDTO parkingSpotDTO) throws Exception {
//
//        if (parkingSpotService.existsByPlateCar(parkingSpotDTO.getLicensePlateCar())) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Carro estacionado em outra vaga!");
//        } else if (parkingSpotService.existsBySpotNumber(parkingSpotDTO.getParkingSpotNumber())) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: vaga já esta ocupada!");
//        } else if (parkingSpotService.existsByApartment(parkingSpotDTO.getApartment())) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: só pode haver uma vaga por apartamento")
//        }

        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDTO, parkingSpotModel);
        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
    }

    @GetMapping
    public List<ParkingSpotModel> getAllParkingSpot() {
        return parkingSpotService.getAll();
    }

    @GetMapping("/{id}")
    public ParkingSpotModel getById(@PathVariable("id") UUID id) throws ChangeSetPersister.NotFoundException {
        return parkingSpotService.getById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody @Valid ParkingSpotDTO parkingSpotDTO) {
        // TODO - not implemented
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable("id") UUID id) {
        // TODO - not implemented
        return null;
    }

}