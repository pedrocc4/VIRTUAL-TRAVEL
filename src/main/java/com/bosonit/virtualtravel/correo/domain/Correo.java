package com.bosonit.virtualtravel.correo.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
public class Correo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Size(min = 5, max = 200)
    private String mensaje;
    private LocalDateTime fecha;
    private String ciudadDestino;
}
