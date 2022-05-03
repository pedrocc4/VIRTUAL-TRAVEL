package com.bosonit.virtualtravel.correo.infraestructure.controller.dto;

import java.time.LocalDateTime;

public record CorreoOutputDTO(String mensaje,
                              LocalDateTime fecha,
                              String ciudadDestino) {
}
