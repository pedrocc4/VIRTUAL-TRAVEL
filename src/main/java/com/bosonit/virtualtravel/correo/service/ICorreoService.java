package com.bosonit.virtualtravel.correo.service;

import com.bosonit.virtualtravel.correo.infraestructure.controller.dto.CorreoOutputDTO;

import java.time.LocalDate;
import java.util.List;

public interface ICorreoService {
    List<CorreoOutputDTO> getCorreos(String ciudadDestino, LocalDate fechaInferior,
                                     LocalDate fechaSuperior, Float horaInferior, Float horaSuperior);
}
