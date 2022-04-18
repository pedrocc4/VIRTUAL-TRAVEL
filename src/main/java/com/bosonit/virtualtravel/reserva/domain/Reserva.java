package com.bosonit.virtualtravel.reserva.domain;

import com.bosonit.virtualtravel.utils.StringPrefixedSequenceIdGenerator;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "reservaGen")
    @GenericGenerator(
            name = "reservaGen",
            strategy = "com.bosonit.virtualtravel.utils.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "RSV"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d")
            })
    private String id;

    @Future
    private LocalDateTime fechaReserva;

    @NotNull
    private String ciudadDestino;
}
