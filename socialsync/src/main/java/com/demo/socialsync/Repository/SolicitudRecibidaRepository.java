package com.demo.socialsync.Repository;

import com.demo.socialsync.Models.SolicitudRecibida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudRecibidaRepository extends JpaRepository<SolicitudRecibida, Long> {

    @Query("SELECT u FROM SolicitudRecibida u WHERE u.id = :id")
    SolicitudRecibida findByIdSoliciudRecibida(@Param("id") Long id);
}
