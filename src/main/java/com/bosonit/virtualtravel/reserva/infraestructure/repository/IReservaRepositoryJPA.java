package com.bosonit.virtualtravel.reserva.infraestructure.repository;

import com.bosonit.virtualtravel.reserva.domain.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReservaRepositoryJPA extends JpaRepository<Reserva, String> {
}
