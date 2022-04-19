package com.bosonit.virtualtravel.reserva.infraestructure.controller;

import com.bosonit.virtualtravel.autobus.infraestructure.controller.dto.output.AutobusOutputDTO;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.input.ReservaInputDTO;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.output.ReservaOutputDTO;
import com.bosonit.virtualtravel.reserva.service.IReservaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("disponible/{ciudad}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AutobusOutputDTO>> reservasDisponibles(
            @PathVariable String ciudad,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate fechaInferior,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate fechaSuperior,
            @RequestParam(required = false) float horaInferior,
            @RequestParam(required = false) float horaSuperior) {
        log.info("Buscando autobuses para: " + ciudad + ", con fecha entre " + fechaInferior + " y " + fechaSuperior
                + " y horario entre " + (int) horaInferior + ":00 y " + (int) horaSuperior + ":00");
        return ResponseEntity.status(HttpStatus.OK).body(
                service.autobusesDisponibles(
                        ciudad, fechaInferior, fechaSuperior, horaInferior, horaSuperior));
    }

    @GetMapping("reserva")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ReservaOutputDTO>> getReservas(
            @RequestParam String ciudad) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getReservas(ciudad));
    }

    @GetMapping("reserva/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ReservaOutputDTO> getReserva(
            @PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getReserva(id));
    }
}
