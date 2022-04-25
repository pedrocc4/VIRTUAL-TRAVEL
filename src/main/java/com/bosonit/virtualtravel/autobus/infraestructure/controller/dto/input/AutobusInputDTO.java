package com.bosonit.virtualtravel.autobus.infraestructure.controller.dto.input;

import java.time.LocalDate;

public record AutobusInputDTO(
        String plazasDisponibles,
        String ciudadDestino,
        Float horaSalida,
        LocalDate fechaSalida
) {
}
