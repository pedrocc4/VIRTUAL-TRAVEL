package com.bosonit.virtualtravel.reserva.service;

import com.bosonit.virtualtravel.autobus.domain.Autobus;
import com.bosonit.virtualtravel.autobus.infraestructure.controller.dto.output.AutobusFullOutputDTO;
import com.bosonit.virtualtravel.autobus.infraestructure.controller.mapper.IAutobusMapper;
import com.bosonit.virtualtravel.autobus.infraestructure.repository.IAutobusRepositoryJPA;
import com.bosonit.virtualtravel.correo.domain.Correo;
import com.bosonit.virtualtravel.correo.infraestructure.repository.ICorreoRepositoryJPA;
import com.bosonit.virtualtravel.reserva.domain.Reserva;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.input.ReservaInputDTO;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.output.ReservaOutputDTO;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.mapper.IReservaMapper;
import com.bosonit.virtualtravel.reserva.infraestructure.repository.IReservaRepositoryJPA;
import com.bosonit.virtualtravel.utils.mail.MailSenderService;
import com.bosonit.virtualtravel.utils.exceptions.NoEncontrado;
import com.bosonit.virtualtravel.utils.kafka.KafkaMessageProducer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.bosonit.virtualtravel.utils.Constantes.NOTFOUND_MESSAGE;
import static com.bosonit.virtualtravel.utils.Constantes.RESERVA_MESSAGE;

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

    @Autowired
    private ICorreoRepositoryJPA correoRepositoryJPA;

    @Autowired
    private KafkaMessageProducer kafka;

    @Autowired
    private MailSenderService mailSender;

    @Override
    public ReservaOutputDTO addReserva(ReservaInputDTO reservaInputDTO) {
        Reserva reserva = mapper.toEntity(reservaInputDTO);
        reserva.setFechaSolicitud(LocalDateTime.now());

        // Comprobamos los autobuses disponibles
        Autobus autobus = autobusRepositoryJPA.autobusesDisponibles(
                        reserva.getCiudadDestino(),
                        reserva.getFechaReserva().getHour())
                .stream()
                .findFirst() // se deberia cambiar en un futuro
                .orElseThrow(() -> new NoEncontrado(
                        "No hay autobuses disponibles para la reserva solicitada"
                ));

        // Asignamos autobus a reserva y disminuimos plazas disponibles
        reserva.setAutobus(autobus);
        autobus.decrementarPlazas(1);

        // Creamos correo para guardar en BD y enviamos reserva
        Correo correo = new Correo();
        correo.setCiudadDestino(reservaInputDTO.ciudadDestino());
        correo.setFecha(LocalDateTime.now());
        correo.setMensaje("Querido " + reserva.getNombre() + " " + reserva.getApellido() +
                ", su reserva a " + correo.getCiudadDestino() + " para el día "
                + reserva.getFechaReserva().getDayOfMonth() + "/"
                + reserva.getFechaReserva().getMonthValue() + "/"
                + reserva.getFechaReserva().getYear() + " ha sido solicitada\nUn saludo. Virtual-Travel");

        correoRepositoryJPA.save(correo);
        kafka.sendMessage("viaje", correo);
        mailSender.sendMail(reserva.getEmail(), "Reserva con Virtual-Travel", correo.getMensaje());

        // Finalmente devolvemos la reserva
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
                () -> new NoEncontrado(RESERVA_MESSAGE + id + NOTFOUND_MESSAGE)));
    }

    @Override
    public ReservaOutputDTO actReserva(String id, ReservaInputDTO reservaInputDTO) {
        Reserva reserva =
                repositoryJPA
                        .findById(id)
                        .orElseThrow(() ->
                                new NoEncontrado(
                                        "RESERVA_MESSAGE + id + NOTFOUND_MESSAGE"));

        // Asignacion de nuevos atributos
        BeanUtils.copyProperties(reservaInputDTO, reserva);
        return mapper.toDTO(repositoryJPA.save(reserva));
    }

    @Override
    public void delReserva(String id) {
        repositoryJPA.delete((repositoryJPA
                .findById(id)
                .orElseThrow(() -> new NoEncontrado
                        ("RESERVA_MESSAGE + id + NOTFOUND_MESSAGE"))));
    }

    @Override
    public List<ReservaOutputDTO> getReservasCiudad(String ciudad) {
        return mapper.toDTOList(repositoryJPA.reservasCiudad(ciudad));
    }

    @Override
    public List<ReservaOutputDTO> getReservas() {
        return mapper.toDTOList(repositoryJPA.findAll());
    }
}
