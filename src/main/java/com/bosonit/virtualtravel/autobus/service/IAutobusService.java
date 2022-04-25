package com.bosonit.virtualtravel.autobus.service;

import com.bosonit.virtualtravel.autobus.infraestructure.controller.dto.input.AutobusInputDTO;
import com.bosonit.virtualtravel.autobus.infraestructure.controller.dto.output.AutobusFullOutputDTO;

import java.util.List;

public interface IAutobusService {
    AutobusFullOutputDTO addAutobus(AutobusInputDTO autobusInputDTO);

    AutobusFullOutputDTO getAutobus(String id);

    AutobusFullOutputDTO actAutobus(String id, AutobusInputDTO autobusInputDTO);

    void delAutobus(String id);

    void crearAutobusDiario();

    List<AutobusFullOutputDTO> getAutobuses();
}
