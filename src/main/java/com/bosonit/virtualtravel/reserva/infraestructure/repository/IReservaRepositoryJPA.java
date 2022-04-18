package com.bosonit.virtualtravel.reserva.infraestructure.repository;

import com.bosonit.virtualtravel.reserva.domain.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IReservaRepositoryJPA extends JpaRepository<Reserva, String> {

    @Query("SELECT r FROM Reserva r WHERE r.fechaReserva " +
            "between ?2 AND ?3 AND r.ciudadDestino = ?1 ")
    List<Reserva> reservasDisponibles(
            String ciudad, LocalDateTime fechaInferior, LocalDateTime fechaSuperior);
}
