package com.bosonit.virtualtravel.reserva;

import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.input.ReservaInputDTO;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.output.ReservaOutputDTO;
import com.bosonit.virtualtravel.reserva.service.IReservaService;
import com.bosonit.virtualtravel.utils.exceptions.NoEncontrado;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertThrows;

@SpringBootTest(classes =
        com.bosonit.virtualtravel.VirtualTravelApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservaServiceTest {
    @Autowired
    private IReservaService service;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    ReservaInputDTO crearReserva() {
        return new ReservaInputDTO(
                "Barcelona",
                LocalDateTime.now().withHour(16),
                "Antonio",
                "Apellido",
                "antonio@gmail.com",
                "692967791");
    }

    ReservaInputDTO crearOtraReserva() {
        return new ReservaInputDTO(
                "Barcelona",
                LocalDateTime.now().withHour(16),
                "Antonio",
                "Apellido",
                "otro@gmail.com",
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
    void autobusesDisponibles() {
        Assertions.assertThat(service.autobusesDisponibles(
                "Barcelona",
                LocalDate.of(2022, 2, 2),
                LocalDate.of(2022, 5, 5),
                12,
                20)).hasSize(1);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void AutobusNoEncontradoExcTest() {
        exception.expect(NoEncontrado.class);
        exception.expectMessage("No hay autobuses disponibles para la reserva solicitada");
        service.autobusesDisponibles(
                "Jaen",
                LocalDate.of(2022, 2, 2),
                LocalDate.of(2022, 5, 5),
                12,
                20);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void ReservaNoEncontradoExcTest() {
        Throwable exception = assertThrows(
                NoEncontrado.class, () -> service.getReserva("ReservaInexistente")
        );
        org.junit.jupiter.api.Assertions.assertEquals(
                "Reserva con id: ReservaInexistente, no encontrado/a", exception.getMessage());
    }


    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getReservaCiudadTest() {
        ReservaInputDTO reservaInputDTO = crearReserva();
        ReservaOutputDTO reservaOutputDTO = service.addReserva(reservaInputDTO);
        Assertions.assertThat(service.getReserva(reservaOutputDTO.id()).email()).isEqualTo(reservaInputDTO.email());
        Assertions.assertThat(service.getReservasCiudad("Barcelona")).hasSize(1);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void actReservaTest() {
        ReservaInputDTO reservaInputDTO = crearReserva();
        ReservaInputDTO actReserva = crearOtraReserva();
        ReservaOutputDTO reservaOutputDTO = service.addReserva(reservaInputDTO);
        Assertions.assertThat(service.actReserva(
                reservaOutputDTO.id(), actReserva).id()).isEqualTo(reservaOutputDTO.id());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void delReservaTest() {
        ReservaInputDTO reservaInputDTO = crearReserva();
        ReservaOutputDTO reservaOutputDTO = service.addReserva(reservaInputDTO);
        Assertions.assertThat(service.getReservas()).hasSize(1);
        service.delReserva(reservaOutputDTO.id());
        Assertions.assertThat(service.getReservas()).isEmpty();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getReservasTest() {
        Assertions.assertThat(service.getReservas()).isEmpty();
        ReservaInputDTO reservaInputDTO = crearReserva();
        service.addReserva(reservaInputDTO);
        Assertions.assertThat(service.getReservas()).hasSize(1);
    }
}
