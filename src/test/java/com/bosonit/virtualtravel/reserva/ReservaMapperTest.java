package com.bosonit.virtualtravel.reserva;

import com.bosonit.virtualtravel.reserva.domain.Reserva;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.input.ReservaInputDTO;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.mapper.IReservaMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes =
        com.bosonit.virtualtravel.VirtualTravelApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
 class ReservaMapperTest {

    @Autowired
    private IReservaMapper mapper;

    ReservaInputDTO crearReserva() {
        return new ReservaInputDTO(
                "Barcelona",
                LocalDateTime.now().withHour(16),
                "Antonio",
                "Apellido",
                "antonio@gmail.com",
                "692967791");
    }

    @Test
    void toDTOList() {
        List<Reserva> reservas = null;
        Assertions.assertThat(mapper.toDTOList(reservas)).isEqualTo(new ArrayList<>());

        List<Reserva> reservas2 = new ArrayList<>();
        Reserva reserva = mapper.toEntity(crearReserva());
        reservas2.add(reserva);
        Assertions.assertThat(mapper.toDTOList(reservas2).get(0)
                .fechaReserva()).isEqualTo(reserva.getFechaReserva());
    }
}
