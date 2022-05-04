package com.bosonit.virtualtravel.reserva.infraestructure.controller;

import com.bosonit.virtualtravel.autobus.infraestructure.controller.dto.output.AutobusFullOutputDTO;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.input.ReservaInputDTO;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.output.ReservaOutputDTO;
import com.bosonit.virtualtravel.reserva.service.IReservaService;
import com.bosonit.virtualtravel.security.LoginController;
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
    private LoginController login;

    @Autowired
    private IReservaService service;

    @PostMapping("reserva")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReservaOutputDTO> addReserva(
            @RequestBody ReservaInputDTO reservaInputDTO, @RequestHeader String authorize) {
        // si la autorizacion no es correcta, forbidden
        if (!login.checkToken(authorize).getStatusCode().equals(HttpStatus.OK))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        log.info("Intentando crear reserva: " + reservaInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addReserva(reservaInputDTO));
    }

    @GetMapping("disponible/{ciudad}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AutobusFullOutputDTO>> reservasDisponibles(
            @PathVariable String ciudad,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate fechaInferior,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate fechaSuperior,
            @RequestParam(required = false) Float horaInferior,
            @RequestParam(required = false) Float horaSuperior) {
        // Establecemos los posibles valores nulos
        // Para la fecha situamos year de esta forma evitando asi problemas con MAX
        if (fechaSuperior == null) {
            fechaSuperior = LocalDate.MAX.withYear(fechaInferior.getYear() + 1);
        }
        // Podriamos usar valores maximos y minimos, pero generan fallos
        // por lo tanto usamos el horario estandar
        if (horaInferior == null) {
            horaInferior = 0.0f;
        }
        if (horaSuperior == null) {
            horaSuperior = 24.0f;
        }
        log.info("Buscando autobuses para: " + ciudad + ", con fecha entre " + fechaInferior + " y " + fechaSuperior
                + " y horario entre " + Math.round(horaInferior) + ":00 y " + Math.round(horaSuperior) + ":00");
        return ResponseEntity.status(HttpStatus.OK).body(
                service.autobusesDisponibles(
                        ciudad, fechaInferior, fechaSuperior, horaInferior, horaSuperior));
    }

    @GetMapping("reserva")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ReservaOutputDTO>> getReservasCiudad(
            @RequestParam String ciudad) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getReservasCiudad(ciudad));
    }

    @GetMapping("reservas")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ReservaOutputDTO>> getReservas() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getReservas());
    }

    @GetMapping("reserva/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ReservaOutputDTO> getReserva(
            @PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getReserva(id));
    }

    @PutMapping("reserva/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ReservaOutputDTO> actReserva(
            @PathVariable String id,
            @RequestBody ReservaInputDTO reservaInputDTO) {
        log.info("Intentando actualizar reserva con id: " + id
                + ", con datos: " + reservaInputDTO);
        return ResponseEntity.status(HttpStatus.OK).body(service.actReserva(id, reservaInputDTO));
    }

    @DeleteMapping("reserva/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> delReserva(
            @PathVariable String id) {
        log.info("Intentando borrar reserva con id: " + id);
        service.delReserva(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
