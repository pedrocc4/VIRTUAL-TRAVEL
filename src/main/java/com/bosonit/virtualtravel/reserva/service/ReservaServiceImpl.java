package com.bosonit.virtualtravel.reserva.service;

import com.bosonit.virtualtravel.reserva.domain.Reserva;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.input.ReservaInputDTO;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.output.ReservaOutputDTO;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.mapper.IReservaMapper;
import com.bosonit.virtualtravel.reserva.infraestructure.repository.IReservaRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaServiceImpl implements IReservaService {

    @Autowired
    private IReservaRepositoryJPA repositoryJPA;

    @Autowired
    private IReservaMapper mapper;

    @Override
    public ReservaOutputDTO addReserva(ReservaInputDTO reservaInputDTO) {
        //FIXME comprobar si hay plazas disponibles
        Reserva reserva = mapper.toEntity(reservaInputDTO);
        reserva.setFechaSolicitud(LocalDateTime.now());
        return mapper.toDTO(repositoryJPA.save(reserva));
    }

    @Override
    public List<ReservaOutputDTO> reservasDisponibles(
            String ciudad, LocalDateTime fechaInferior, LocalDateTime fechaSuperior) {
        return mapper.toDTOList(
                repositoryJPA.reservasDisponibles(ciudad, fechaInferior, fechaSuperior));
    }
}
