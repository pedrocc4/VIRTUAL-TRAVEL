package com.bosonit.virtualtravel.correo.infraestructure.repository;

import com.bosonit.virtualtravel.correo.domain.Correo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICorreoRepositoryJPA extends JpaRepository<Correo, Integer> {
}
