package com.demo.socialsync.Repository;

import com.demo.socialsync.Models.SolicitudEnviada;
import com.demo.socialsync.Models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudEnviadaRepository extends JpaRepository<SolicitudEnviada, Long> {

    @Query("SELECT u FROM SolicitudEnviada u WHERE u.id = :id")
    SolicitudEnviada findByIdSoliciudEnviada(@Param("id") Long id);
}
