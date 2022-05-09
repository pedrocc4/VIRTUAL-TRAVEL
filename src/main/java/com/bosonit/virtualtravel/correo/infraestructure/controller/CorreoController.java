package com.bosonit.virtualtravel.correo.infraestructure.controller;

import com.bosonit.virtualtravel.correo.infraestructure.controller.dto.input.CorreoInputDTO;
import com.bosonit.virtualtravel.correo.infraestructure.controller.dto.output.CorreoOutputDTO;
import com.bosonit.virtualtravel.correo.service.ICorreoService;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.output.ReservaOutputDTO;
import com.bosonit.virtualtravel.security.LoginController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v0")
public class CorreoController {

    @Autowired
    private ICorreoService service;

    @Autowired
    private LoginController login;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("correos")
    public ResponseEntity<List<CorreoOutputDTO>> correos(
            @RequestParam(required = false) String ciudadDestino,
            @RequestParam LocalDate fechaInferior,
            @RequestParam LocalDate fechaSuperior,
            @RequestParam(required = false) Float horaInferior,
            @RequestParam(required = false) Float horaSuperior,
            @RequestHeader String authorize) {

        // si la autorizacion no es correcta, forbidden
        if (!login.checkToken(authorize).getStatusCode().equals(HttpStatus.OK))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

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

        log.info("Buscando correos para: " + ciudadDestino + ", con fecha entre " + fechaInferior + " y " + fechaSuperior
                + " y horario entre " + Math.round(horaInferior) + ":00 y " + Math.round(horaSuperior) + ":00");

        return ResponseEntity.status(HttpStatus.OK).body(service.getCorreos(
                ciudadDestino, fechaInferior, fechaSuperior, horaInferior, horaSuperior)
        );
    }

    @PutMapping("correos")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ReservaOutputDTO> reenvioCorreos(@RequestBody CorreoInputDTO correoInputDTO,
                                                           @RequestHeader String authorize) {
        // si la autorizacion no es correcta, forbidden
        if (!login.checkToken(authorize).getStatusCode().equals(HttpStatus.OK))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();


        return ResponseEntity.status(HttpStatus.OK).body(service.reenvioCorreos(correoInputDTO));
    }
}
