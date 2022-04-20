package com.bosonit.virtualtravel.correo.infraestructure.repository;

import com.bosonit.virtualtravel.correo.domain.Correo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICorreoRepositoryJPA extends JpaRepository<Correo, Integer> {
}
