package com.bosonit.virtualtravel.reserva.service;

import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.input.ReservaInputDTO;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.output.ReservaOutputDTO;

public interface IReservaService {
    ReservaOutputDTO addReserva(ReservaInputDTO reservaInputDTO);
}
