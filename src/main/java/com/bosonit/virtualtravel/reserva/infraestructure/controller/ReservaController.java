package com.bosonit.virtualtravel.reserva.infraestructure.controller;

import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.input.ReservaInputDTO;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.output.ReservaOutputDTO;
import com.bosonit.virtualtravel.reserva.service.IReservaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v0")
public class ReservaController {

    @Autowired
    private IReservaService service;

    @PostMapping("reserva")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReservaOutputDTO> addReserva(
            @RequestBody ReservaInputDTO reservaInputDTO) {
        log.info("Intentando crear reserva: " + reservaInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addReserva(reservaInputDTO));
    }

    //FIXME crear entidad ¿autobus? para gestionar plazas
    @GetMapping("disponible/{ciudad}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ReservaOutputDTO>> reservasDisponibles(
            @PathVariable String ciudad,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                    LocalDateTime fechaInferior,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                    LocalDateTime fechaSuperior) {
        log.info("Buscando reservas para: " + ciudad + ", con fecha entre " + fechaInferior + " y " + fechaSuperior);
        return ResponseEntity.status(HttpStatus.OK).body(
                service.reservasDisponibles(ciudad, fechaInferior, fechaSuperior));
    }
}
