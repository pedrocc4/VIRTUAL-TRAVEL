package com.bosonit.virtualtravel.correo.infraestructure.controller.mapper;


import com.bosonit.virtualtravel.correo.domain.Correo;
import com.bosonit.virtualtravel.correo.infraestructure.controller.dto.input.CorreoInputDTO;
import com.bosonit.virtualtravel.correo.infraestructure.controller.dto.output.CorreoOutputDTO;
import org.mapstruct.Mapper;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
@Validated
public interface ICorreoMapper {

    @Valid Correo toEntity(CorreoInputDTO correoInputDTO);

    CorreoOutputDTO toDTO(@Valid Correo correo);

    default List<CorreoOutputDTO> toDTOList(@Valid List<Correo> correos) {
        if (correos == null) {
            return new ArrayList<>();
        }
        return correos.stream().map(this::toDTO).toList();
    }
}
