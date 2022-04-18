package com.bosonit.virtualtravel.reserva.infraestructure.controller.mapper;

import com.bosonit.virtualtravel.reserva.domain.Reserva;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.input.ReservaInputDTO;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.output.ReservaOutputDTO;
import org.mapstruct.Mapper;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
@Validated
public interface IReservaMapper {

    @Valid Reserva toEntity(ReservaInputDTO ReservaInputDTO);

    ReservaOutputDTO toDTO(@Valid Reserva Reserva);

    default List<ReservaOutputDTO> toDTOList(@Valid List<Reserva> Reservas) {
        if (Reservas == null) {
            return new ArrayList<>();
        }
        return Reservas.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
