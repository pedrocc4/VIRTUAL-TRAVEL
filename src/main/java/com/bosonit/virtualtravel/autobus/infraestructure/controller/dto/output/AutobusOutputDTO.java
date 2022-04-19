package com.bosonit.virtualtravel.autobus.infraestructure.controller.dto.output;

import java.time.LocalDate;

public record AutobusOutputDTO(
        String plazasDisponibles,
        LocalDate fechaSalida) {
}
