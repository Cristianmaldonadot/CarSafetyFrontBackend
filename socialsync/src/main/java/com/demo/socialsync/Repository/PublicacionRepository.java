package com.demo.socialsync.Repository;

import com.demo.socialsync.Models.Publicacion;
import com.demo.socialsync.Models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Integer> {

    @Transactional
    List<Publicacion> findByUsuario(Usuario username);
}
