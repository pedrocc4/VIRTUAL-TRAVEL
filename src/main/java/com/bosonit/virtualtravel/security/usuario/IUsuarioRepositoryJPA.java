package com.bosonit.virtualtravel.security.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepositoryJPA extends JpaRepository<Usuario, String> {

    @Query("SELECT u FROM Usuario u WHERE u.token = ?1")
    Usuario checkToken(String token);
}
