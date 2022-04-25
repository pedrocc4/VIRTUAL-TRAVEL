package com.bosonit.virtualtravel.reserva.domain;

import com.bosonit.virtualtravel.autobus.domain.Autobus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

import static com.bosonit.virtualtravel.utils.StringPrefixedSequenceIdGenerator.*;

@Entity
@Data
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "reservaGen")
    @GenericGenerator(
            name = "reservaGen",
            strategy = "com.bosonit.virtualtravel.utils.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = INCREMENT_PARAM, value = "1"),
                    @Parameter(name = VALUE_PREFIX_PARAMETER, value = "RSV"),
                    @Parameter(name = NUMBER_FORMAT_PARAMETER, value = "%05d")
            })
    private String id;

    // Datos reserva

    @Future
    private LocalDateTime fechaReserva;     //> fecha a reservar

    private LocalDateTime fechaSolicitud;   //> momento en el que se solicita la reserva

    @NotNull
    private String ciudadDestino;

    // Datos solicitante

    @NotNull
    private String nombre;

    @NotNull
    private String apellido;

    @NotNull
    @Email
    //@Column(unique = true) // tendria sentido si fuera entidad usuario (no en reserva)
    private String email;

    @Size(min = 9, max = 13)
    @Pattern(regexp = "^(\\+34|0034|34)?[6789]\\d{8}$",
            message = "Formato de telefono incorrecto")
    private String telefono;

    // Relacion con tablas

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservas")
    private Autobus autobus;
}
