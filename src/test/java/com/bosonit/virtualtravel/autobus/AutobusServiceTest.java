package com.bosonit.virtualtravel.autobus;

import com.bosonit.virtualtravel.autobus.infraestructure.controller.dto.input.AutobusInputDTO;
import com.bosonit.virtualtravel.autobus.infraestructure.controller.dto.output.AutobusFullOutputDTO;
import com.bosonit.virtualtravel.autobus.service.IAutobusService;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.input.ReservaInputDTO;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.output.ReservaOutputDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest(classes =
        com.bosonit.virtualtravel.VirtualTravelApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AutobusServiceTest {

    @Autowired
    private IAutobusService service;

    AutobusInputDTO crearAutobus() {
        return new AutobusInputDTO(
                "40",
                "Madrid",
                16.0f,
                LocalDate.now().plusMonths(1)
        );
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void crearAutobusDiarioTest() {
        // Hay 3 autobuses por defecto
        Assertions.assertThat(service.getAutobuses()).hasSize(3);
        service.crearAutobusDiario();
        // Este metodo crea 4 diarios, por lo tanto 7
        Assertions.assertThat(service.getAutobuses()).hasSize(7);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void addAutobusTest() {
        Assertions.assertThat(service.getAutobuses()).hasSize(3);
        AutobusInputDTO autobus = crearAutobus();
        service.addAutobus(autobus);
        Assertions.assertThat(service.getAutobuses()).hasSize(4);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void actAutobusTest() {
        AutobusInputDTO autobus = crearAutobus();
        AutobusFullOutputDTO autobusFullOutputDTO = service.addAutobus(autobus);
        Assertions.assertThat(
                service.actAutobus("BUS1", autobus).ciudadDestino()).isEqualTo(autobus.ciudadDestino());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void delAutobusTest() {
        AutobusInputDTO autobusInputDTO = crearAutobus();
        AutobusFullOutputDTO autobusFullOutputDTO = service.addAutobus(autobusInputDTO);
        Assertions.assertThat(service.getAutobuses()).hasSize(4);
        service.delAutobus(autobusFullOutputDTO.id());
        Assertions.assertThat(service.getAutobuses()).hasSize(3);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getAutobusesTest() {
        Assertions.assertThat(service.getAutobuses()).hasSize(3);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getAutobus() {
        Assertions.assertThat(service.getAutobus("BUS1")).isIn(service.getAutobuses());
    }
}
