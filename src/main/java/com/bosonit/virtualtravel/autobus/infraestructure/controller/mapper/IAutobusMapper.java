package com.bosonit.virtualtravel.autobus.infraestructure.controller.mapper;

import com.bosonit.virtualtravel.autobus.domain.Autobus;
import com.bosonit.virtualtravel.autobus.infraestructure.controller.dto.input.AutobusInputDTO;
import com.bosonit.virtualtravel.autobus.infraestructure.controller.dto.output.AutobusFullOutputDTO;
import com.bosonit.virtualtravel.autobus.infraestructure.controller.dto.output.AutobusOutputDTO;
import org.mapstruct.Mapper;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
@Validated
public interface IAutobusMapper {

    @Valid Autobus toEntity(AutobusInputDTO autobusInputDTO);

    AutobusOutputDTO toDTO(@Valid Autobus autobus);

    AutobusFullOutputDTO toFullDTO(@Valid Autobus autobus);

    default List<AutobusOutputDTO> toDTOList(@Valid List<Autobus> autobuses) {
        if (autobuses == null) {
            return new ArrayList<>();
        }
        return autobuses.stream().map(this::toDTO).toList();
    }

    default List<AutobusFullOutputDTO> toFullDTOList(@Valid List<Autobus> autobuses) {
        if (autobuses == null) {
            return new ArrayList<>();
        }
        return autobuses.stream().map(this::toFullDTO).toList();
    }
}
