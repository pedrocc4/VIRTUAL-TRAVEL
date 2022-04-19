package com.bosonit.virtualtravel.autobus.infraestructure.repository;

import com.bosonit.virtualtravel.autobus.domain.Autobus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface IAutobusRepositoryJPA extends JpaRepository<Autobus, String> {

    @Query("SELECT a FROM Autobus a WHERE a.ciudadDestino = ?1 AND a.horaSalida = ?2")
    List<Autobus> autobusesDisponibles(String ciudad, float salida);


    @Query("SELECT a FROM Autobus a WHERE a.fechaSalida " +
            "BETWEEN ?2 AND ?3 AND a.ciudadDestino = ?1 " +
            "AND a.horaSalida > ?4 AND a.horaSalida < ?5")
    List<Autobus> autobusesDisponiblesFecha(
            String ciudad, LocalDate fechaInferior, LocalDate fechaSuperior,
            float horaInferior, float horaSuperior);
}
