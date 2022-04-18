package com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.input;

import java.time.LocalDateTime;

public record ReservaInputDTO(
        String ciudadDestino,
        LocalDateTime fechaReserva,
        LocalDateTime fechaSolicitud,
        String nombre,
        String apellido,
        String email,
        String telefono) {
}
