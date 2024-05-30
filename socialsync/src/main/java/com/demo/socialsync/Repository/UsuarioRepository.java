package com.demo.socialsync.Repository;

import com.demo.socialsync.Models.Usuario;
import com.demo.socialsync.Models.UsuarioJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Transactional
    Optional<Usuario> findByUsername(String username);

    /*@Query("SELECT new com.demo.socialsync.Models.Usuario(u.idUsuario, u.username, u.fotoPerfil) FROM Usuario u WHERE u.id = :idUsuario")
    Usuario obtenerUsuarioConNombrePorId(@Param("idUsuario") Long idUsuario);*/

    @Query("SELECT u FROM Usuario u WHERE u.id = :idUsuario")
    Usuario findByIdUsuario(@Param("idUsuario") Long idUsuario);

    @Query("SELECT u.amigos FROM Usuario u WHERE u.idUsuario = :idUsuario")
    List<Usuario> findAmigosByUsuarioId(@Param("idUsuario") Long idUsuario);

}
