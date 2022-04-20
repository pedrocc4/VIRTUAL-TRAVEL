package com.bosonit.virtualtravel.reserva.infraestructure.controller.mapper;

import com.bosonit.virtualtravel.reserva.domain.Reserva;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.input.ReservaInputDTO;
import com.bosonit.virtualtravel.reserva.infraestructure.controller.dto.output.ReservaOutputDTO;
import org.mapstruct.Mapper;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
@Validated
public interface IReservaMapper {

    @Valid Reserva toEntity(ReservaInputDTO reservaInputDTO);

    ReservaOutputDTO toDTO(@Valid Reserva reserva);

    default List<ReservaOutputDTO> toDTOList(@Valid List<Reserva> reservas) {
        if (reservas == null) {
            return new ArrayList<>();
        }
        return reservas.stream().map(this::toDTO).toList();
    }
}
