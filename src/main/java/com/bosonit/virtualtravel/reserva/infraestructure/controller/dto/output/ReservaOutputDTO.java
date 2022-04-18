package com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.output;

import java.time.LocalDateTime;

public record ReservaOutputDTO(
        String id,
        String ciudadDestino,
        LocalDateTime fechaReserva) {
}
