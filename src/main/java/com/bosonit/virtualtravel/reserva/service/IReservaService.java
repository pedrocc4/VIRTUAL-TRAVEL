package com.bosonit.virtualtravel.reserva.service;

import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.input.ReservaInputDTO;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.output.ReservaOutputDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface IReservaService {
    ReservaOutputDTO addReserva(ReservaInputDTO reservaInputDTO);

    List<ReservaOutputDTO> reservasDisponibles(
            String ciudad, LocalDateTime fechaInferior,
            LocalDateTime fechaSuperior);
}
