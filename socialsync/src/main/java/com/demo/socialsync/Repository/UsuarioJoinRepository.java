package com.demo.socialsync.Repository;

import com.demo.socialsync.Models.UsuarioJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UsuarioJoinRepository extends JpaRepository<UsuarioJoin, Long> {

    @Query("SELECT new com.demo.socialsync.Models.UsuarioJoin(u.id, u.username, u.fotoPerfil) FROM Usuario u")
    List<UsuarioJoin> listarUsuariosConNombre();

    @Query("SELECT new com.demo.socialsync.Models.UsuarioJoin(u.id, u.username, u.fotoPerfil) FROM Usuario u WHERE u.id = :id")
    UsuarioJoin obtenerUsuarioConNombrePorId(@Param("id") Long id);
}
