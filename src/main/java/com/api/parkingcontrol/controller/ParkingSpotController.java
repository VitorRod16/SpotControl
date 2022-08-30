package com.api.parkingcontrol.controller;

import com.api.parkingcontrol.dto.ParkingSpotDTO;
import com.api.parkingcontrol.model.ParkingSpotModel;
import com.api.parkingcontrol.service.ParkingSpotService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/park")
public class ParkingSpotController {

    @Autowired
    private ParkingSpotService parkingSpotService;

    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDTO parkingSpotDTO) {
        if (parkingSpotService.existsByPlateCar(parkingSpotDTO.getPlateCar())) {
            var plateCar = parkingSpotDTO.getPlateCar();
            var msg = String.format("Erro: Carro %s estacionado em outra vaga!", plateCar);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
        } else if (parkingSpotService.existsByNumber(parkingSpotDTO.getNumber())) {
            var number = parkingSpotDTO.getNumber();
            var msg = String.format("Erro: A vaga %s já esta sendo usada!", number);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
        } else if (parkingSpotService.existsByApartmentAndBlock(parkingSpotDTO.getApartment(), parkingSpotDTO.getBlock())) {
            var apartment = parkingSpotDTO.getApartment();
            var block = parkingSpotDTO.getBlock();
            var msg = String.format("Erro: Apartamento %s do bloco %s já possui um carro estacionado!", apartment, block);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
        }
        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDTO, parkingSpotModel);
        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
    }

    @GetMapping
    public ResponseEntity<Page<ParkingSpotModel>> getAllParkingSpot(@PageableDefault(page = 0, size = 10, sort = "number", direction = Sort.Direction.ASC)  Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") UUID id) {
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.getById(id);
        if (parkingSpotModelOptional.isEmpty()) {
            var msg = String.format("Erro: Id: %s não encontrado!", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable("id") UUID id) {
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.getById(id);
        if (parkingSpotModelOptional.isEmpty()) {
            var msg = String.format("Erro: id %s não encontrado!", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
        parkingSpotService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Vaga deletada com sucesso!");
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> putSpot(@PathVariable(value = "id") UUID id,
                                          @RequestBody @Valid ParkingSpotDTO parkingSpotDTO) {
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.getById(id);
        if (parkingSpotModelOptional.isEmpty()) {
            var msg = String.format("Erro: id %s não encontrado!", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }

        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDTO, parkingSpotModel);
        parkingSpotModel.setId(parkingSpotModelOptional.get().getId());
        parkingSpotModel.setRegistrationDate(parkingSpotModelOptional.get().getRegistrationDate());

        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel));
    }
}