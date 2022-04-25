package com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.output;

import com.bosonit.virtualtravel.autobus.domain.Autobus;

import java.time.LocalDateTime;

public record ReservaOutputDTO(
        String id,
        String ciudadDestino,
        LocalDateTime fechaReserva,
        LocalDateTime fechaSolicitud,
        String nombre,
        String apellido,
        String email,
        String telefono,
        Autobus autobus) {
}
