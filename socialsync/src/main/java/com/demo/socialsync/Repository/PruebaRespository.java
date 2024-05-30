package com.demo.socialsync.Repository;

import com.demo.socialsync.Models.Prueba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PruebaRespository extends JpaRepository<Prueba, Integer> {
}
