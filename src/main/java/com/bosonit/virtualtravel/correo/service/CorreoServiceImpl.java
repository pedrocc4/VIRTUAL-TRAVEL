package com.bosonit.virtualtravel.correo.service;

import com.bosonit.virtualtravel.correo.infraestructure.controller.dto.output.CorreoOutputDTO;
import com.bosonit.virtualtravel.correo.infraestructure.controller.dto.input.CorreoInputDTO;
import com.bosonit.virtualtravel.correo.infraestructure.controller.mapper.ICorreoMapper;
import com.bosonit.virtualtravel.correo.infraestructure.repository.ICorreoRepositoryJPA;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.output.ReservaOutputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CorreoServiceImpl implements ICorreoService {

    @Autowired
    private ICorreoRepositoryJPA repositoryJPA;

    @Autowired
    private ICorreoMapper mapper;

    @Override
    public List<CorreoOutputDTO> getCorreos(String ciudadDestino, LocalDate fechaInferior,
                                            LocalDate fechaSuperior, Float horaInferior, Float horaSuperior) {
        //TODO implementar funcion repo para estos datos
        return mapper.toDTOList(repositoryJPA.findAll());
    }

    @Override
    public ReservaOutputDTO reenvioCorreos(CorreoInputDTO correoInputDTO) {
        //TODO implementation
        return null;
    }
}
