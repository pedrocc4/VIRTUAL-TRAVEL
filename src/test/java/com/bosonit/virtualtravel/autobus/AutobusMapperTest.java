package com.bosonit.virtualtravel.autobus;

import com.bosonit.virtualtravel.autobus.domain.Autobus;
import com.bosonit.virtualtravel.autobus.infraestructure.controller.dto.input.AutobusInputDTO;
import com.bosonit.virtualtravel.autobus.infraestructure.controller.mapper.IAutobusMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes =
        com.bosonit.virtualtravel.VirtualTravelApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
 class AutobusMapperTest {
    @Autowired
    private IAutobusMapper mapper;

    AutobusInputDTO crearAutobus() {
        return new AutobusInputDTO(
                "40",
                "Madrid",
                16.0f,
                LocalDate.now().plusMonths(1)
        );
    }

    @Test
    void toDTOList() {
        List<Autobus> autobuses = null;
        Assertions.assertThat(mapper.toDTOList(autobuses)).isEqualTo(new ArrayList<>());

        List<Autobus> autobuses2 = new ArrayList<>();
        Autobus autobus = mapper.toEntity(crearAutobus());
        autobuses2.add(autobus);
        Assertions.assertThat(mapper.toDTOList(autobuses2).get(0)
                .fechaSalida()).isEqualTo(autobus.getFechaSalida());
    }

    @Test
    void toFullDTOList() {
        List<Autobus> autobuses = null;
        Assertions.assertThat(mapper.toFullDTOList(autobuses)).isEqualTo(new ArrayList<>());

        List<Autobus> autobuses2 = new ArrayList<>();
        Autobus autobus = mapper.toEntity(crearAutobus());
        autobuses2.add(autobus);
        Assertions.assertThat(mapper.toFullDTOList(autobuses2).get(0)
                .ciudadDestino()).isEqualTo(autobus.getCiudadDestino());
    }
}
