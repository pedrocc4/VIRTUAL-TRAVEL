package com.bosonit.virtualtravel.autobus.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.bosonit.virtualtravel.utils.StringPrefixedSequenceIdGenerator.*;

@Entity
@Data
public class Autobus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "autobusGen")
    @GenericGenerator(
            name = "autobusGen",
            strategy = "com.bosonit.virtualtravel.utils.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = INCREMENT_PARAM, value = "1"),
                    @Parameter(name = VALUE_PREFIX_PARAMETER, value = "BUS"),
                    @Parameter(name = NUMBER_FORMAT_PARAMETER, value = "%05d")
            })
    private String id;

    @Min(value= 0, message = "No pueden quedar menos de cero plazas")
    @Max(value = 40, message = "No pueden haber mas de 40 plazas")
    private int plazasDisponibles;

    @Column(columnDefinition = "VARCHAR(10) CHECK (ciudad_Destino IN ('Valencia', 'Madrid', 'Barcelona', 'Bilbao'))")
    private String ciudadDestino;

    @Column(columnDefinition = "FLOAT CHECK (hora_Salida IN (8.00, 12.0, 16.0, 20.0))")
    private float horaSalida;

    @NotNull
    private LocalDate fechaSalida;
}
