package com.bosonit.virtualtravel.reserva;

import com.bosonit.virtualtravel.reserva.domain.Reserva;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.Set;


@SpringBootTest(classes =
        com.bosonit.virtualtravel.VirtualTravelApplication.class)
class ReservaTest {

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void validateReservaTest() {
        Reserva reserva = new Reserva();
        reserva.setFechaReserva(LocalDateTime.of(2022, 5, 5, 12, 12));
        reserva.setFechaSolicitud(LocalDateTime.now());
        reserva.setApellido("antonio");
        reserva.setEmail("pedro@gmail.com");
        reserva.setNombre("Pedro");
        reserva.setId("EST001");
        reserva.setCiudadDestino("Barcelona");

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Reserva>> violations = validator.validate(reserva);
        Assertions.assertThat(violations).isEmpty();
    }
}
