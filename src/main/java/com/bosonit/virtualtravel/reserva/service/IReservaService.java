package com.bosonit.virtualtravel.reserva.service;

import com.bosonit.virtualtravel.autobus.infraestructure.controller.dto.output.AutobusFullOutputDTO;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.input.ReservaInputDTO;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.output.ReservaOutputDTO;

import java.time.LocalDate;
import java.util.List;

public interface IReservaService {
    ReservaOutputDTO addReserva(ReservaInputDTO reservaInputDTO);

    List<AutobusFullOutputDTO> autobusesDisponibles(
            String ciudad, LocalDate fechaInferior,
            LocalDate fechaSuperior, float horaInferior,
            float horaSuperior);

    ReservaOutputDTO getReserva(String id);

    List<ReservaOutputDTO> getReservas(String ciudad);
}
