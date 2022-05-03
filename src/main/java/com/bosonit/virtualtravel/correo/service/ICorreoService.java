package com.bosonit.virtualtravel.correo.service;

import com.bosonit.virtualtravel.correo.infraestructure.controller.dto.input.CorreoInputDTO;
import com.bosonit.virtualtravel.correo.infraestructure.controller.dto.output.CorreoOutputDTO;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.output.ReservaOutputDTO;

import java.time.LocalDate;
import java.util.List;

public interface ICorreoService {
    List<CorreoOutputDTO> getCorreos(String ciudadDestino, LocalDate fechaInferior,
                                     LocalDate fechaSuperior, Float horaInferior, Float horaSuperior);
    ReservaOutputDTO reenvioCorreos(CorreoInputDTO correoInputDTO);
}
