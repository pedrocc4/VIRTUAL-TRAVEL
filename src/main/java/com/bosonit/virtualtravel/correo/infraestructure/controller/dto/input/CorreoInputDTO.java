package com.bosonit.virtualtravel.correo.infraestructure.controller.dto.input;

import java.time.LocalDateTime;

public record CorreoInputDTO(String mensaje,
                            LocalDateTime fecha,
                            String ciudadDestino)  {
}
