package com.bosonit.virtualtravel.autobus.infraestructure.controller.mapper;

import com.bosonit.virtualtravel.autobus.domain.Autobus;
import com.bosonit.virtualtravel.autobus.infraestructure.controller.dto.input.AutobusInputDTO;
import com.bosonit.virtualtravel.autobus.infraestructure.controller.dto.output.AutobusOutputDTO;
import org.mapstruct.Mapper;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
@Validated
public interface IAutobusMapper {

    @Valid Autobus toEntity(AutobusInputDTO AutobusInputDTO);

    AutobusOutputDTO toDTO(@Valid Autobus Autobus);

    default List<AutobusOutputDTO> toDTOList(@Valid List<Autobus> Autobuss) {
        if (Autobuss == null) {
            return new ArrayList<>();
        }
        return Autobuss.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
