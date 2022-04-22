package com.bosonit.virtualtravel.autobus.infraestructure.controller.dto.output;

import java.time.LocalDate;

public record AutobusFullOutputDTO(
        String id,
        String plazasDisponibles,
        String ciudadDestino,
        float horaSalida,
        LocalDate fechaSalida) {
}
