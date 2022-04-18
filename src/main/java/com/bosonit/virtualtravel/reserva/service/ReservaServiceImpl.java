package com.bosonit.virtualtravel.reserva.service;

import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.input.ReservaInputDTO;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.output.ReservaOutputDTO;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.mapper.IReservaMapper;
import com.bosonit.virtualtravel.reserva.infraestructure.repository.IReservaRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservaServiceImpl implements IReservaService {

    @Autowired
    private IReservaRepositoryJPA repositoryJPA;

    @Autowired
    private IReservaMapper mapper;

    @Override
    public ReservaOutputDTO addReserva(ReservaInputDTO reservaInputDTO) {
        return mapper.toDTO(repositoryJPA.save(mapper.toEntity(reservaInputDTO)));
    }
}
