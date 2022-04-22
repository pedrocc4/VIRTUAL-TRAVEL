package com.bosonit.virtualtravel.reserva.service;

import com.bosonit.virtualtravel.autobus.domain.Autobus;
import com.bosonit.virtualtravel.autobus.infraestructure.controller.dto.output.AutobusFullOutputDTO;
import com.bosonit.virtualtravel.autobus.infraestructure.controller.mapper.IAutobusMapper;
import com.bosonit.virtualtravel.autobus.infraestructure.repository.IAutobusRepositoryJPA;
import com.bosonit.virtualtravel.reserva.domain.Reserva;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.input.ReservaInputDTO;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.output.ReservaOutputDTO;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.mapper.IReservaMapper;
import com.bosonit.virtualtravel.reserva.infraestructure.repository.IReservaRepositoryJPA;
import com.bosonit.virtualtravel.utils.exceptions.NoEncontrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaServiceImpl implements IReservaService {

    @Autowired
    private IReservaRepositoryJPA repositoryJPA;

    @Autowired
    private IAutobusRepositoryJPA autobusRepositoryJPA;

    @Autowired
    private IReservaMapper mapper;

    @Autowired
    private IAutobusMapper autobusMapper;

    @Override
    public ReservaOutputDTO addReserva(ReservaInputDTO reservaInputDTO) {
        Reserva reserva = mapper.toEntity(reservaInputDTO);
        reserva.setFechaSolicitud(LocalDateTime.now());

        // Comprobamos los autobuses disponibles
        //FIXME seria mucho mejor devolver un autobusOutputDTOList para darle
        // al usuario la posibilidad de elegir uno

        // FIXME tambien podemos suponer que a este metodo solo se llama
        //  cuando se han mostrado ya los autobuses al usuario,
        //  entonces si tendria sentidoverificar que no haya ningun fallo y lanzar excepcion
        Autobus autobus = autobusRepositoryJPA.autobusesDisponibles(
                        reserva.getCiudadDestino(),
                        reserva.getFechaReserva().getHour())
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoEncontrado(
                        "No hay autobuses disponibles para la reserva solicitada"
                ));

        // Asignamos autobus a reserva y diminuimos plazas disponibles
        //reserva.setAutobus(autobus);
        autobus.setPlazasDisponibles(autobus.getPlazasDisponibles() - 1);
        return mapper.toDTO(repositoryJPA.save(reserva));
    }

    @Override
    public List<AutobusFullOutputDTO> autobusesDisponibles(
            String ciudad, LocalDate fechaInferior, LocalDate fechaSuperior,
            float horaInferior, float horaSuperior) {
        return autobusMapper.toFullDTOList(
                autobusRepositoryJPA.autobusesDisponiblesFecha(
                        ciudad, fechaInferior, fechaSuperior, horaInferior, horaSuperior));
    }

    @Override
    public ReservaOutputDTO getReserva(String id) {
        return mapper.toDTO(repositoryJPA.findById(id).orElseThrow(
                () -> new NoEncontrado("Reserva con id: " + id + ", no encontrada")));
    }

    @Override
    public List<ReservaOutputDTO> getReservas(String ciudad) {
        return mapper.toDTOList(repositoryJPA.reservasCiudad(ciudad));
    }
}
