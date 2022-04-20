package com.bosonit.virtualtravel.reserva;

import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.input.ReservaInputDTO;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.output.ReservaOutputDTO;
import com.bosonit.virtualtravel.reserva.service.IReservaService;
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
class ReservaServiceTest {
    @Autowired
    private IReservaService service;

    ReservaInputDTO crearReserva() {
        return new ReservaInputDTO(
                "Barcelona",
                LocalDateTime.of(2022, 4, 24, 16, 0, 0),
                LocalDateTime.now(),
                "Antonio",
                "Apellido",
                "antonio@gmail.com",
                "692967791");
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void addReservaTest() {
        ReservaInputDTO reserva = crearReserva();

        // Agregamos y comprobamos su persistencia
        ReservaOutputDTO reservaOutputDTO = service.addReserva(reserva);
        Assertions.assertThat(
                service.getReserva(reservaOutputDTO.id()).email())
                .isEqualTo(reserva.email());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void autobusesDisponibleS(){
        Assertions.assertThat(service.autobusesDisponibles(
                "Barcelona",
                LocalDate.of(2022,2,2),
                LocalDate.of(2022,5,5),
                12,
                20)).hasSize(1);
    }
}
