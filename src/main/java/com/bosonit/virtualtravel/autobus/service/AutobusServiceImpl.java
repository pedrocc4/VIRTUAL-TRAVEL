package com.bosonit.virtualtravel.autobus.service;

import com.bosonit.virtualtravel.autobus.domain.Autobus;
import com.bosonit.virtualtravel.autobus.infraestructure.controller.dto.input.AutobusInputDTO;
import com.bosonit.virtualtravel.autobus.infraestructure.controller.dto.output.AutobusFullOutputDTO;
import com.bosonit.virtualtravel.autobus.infraestructure.controller.mapper.IAutobusMapper;
import com.bosonit.virtualtravel.autobus.infraestructure.repository.IAutobusRepositoryJPA;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class AutobusServiceImpl implements IAutobusService {

    @Autowired
    private IAutobusRepositoryJPA repositoryJPA;

    @Autowired
    private IAutobusMapper mapper;

    @Override
    public AutobusFullOutputDTO addAutobus(AutobusInputDTO autobusInputDTO) {
        return null;
    }

    @Override
    public AutobusFullOutputDTO getAutobus(String id) {
        return null;
    }

    @Override
    public void actAutobus(String id, AutobusInputDTO autobusInputDTO) {

    }

    @Override
    public void delAutobus(String id) {

    }

    @Override
    @Transactional
    @Scheduled(cron = "0 0 0 * * *")  //se actualiza todos los días a las 00:00:00
    public void crearAutobusDiario() {
        // Definimos 4 autobuses
        Autobus autobus = new Autobus();
        Autobus autobus1 = new Autobus();
        Autobus autobus2 = new Autobus();
        Autobus autobus3 = new Autobus();

        // Creamos atributos compartidos
        LocalDate fechaActual = LocalDate.now();
        autobus.setPlazasDisponibles(40);
        autobus.setCiudadDestino("Barcelona");
        autobus.setFechaSalida(fechaActual.plusDays(1));

        // Copiamos atributos (hay que hacerlo asi, no copiar objeto [autb = autb2]
        BeanUtils.copyProperties(autobus, autobus1);
        BeanUtils.copyProperties(autobus, autobus2);
        BeanUtils.copyProperties(autobus, autobus3);

        // Persistimos un autobus para cada hora
        autobus.setHoraSalida(8.0f);
        autobus1.setHoraSalida(12.0f);
        autobus2.setHoraSalida(16.0f);
        autobus3.setHoraSalida(20.0f);
        repositoryJPA.save(autobus);
        repositoryJPA.save(autobus1);
        repositoryJPA.save(autobus2);
        repositoryJPA.save(autobus3);
    }

    @Override
    public List<AutobusFullOutputDTO> getAutobuses() {
        return mapper.toFullDTOList(repositoryJPA.findAll());
    }
}
