package com.demo.socialsync.Repository;

import com.demo.socialsync.Models.Megusta;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MegustaRepository extends JpaRepository<Megusta, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Megusta p WHERE p.id = ?1")
    void eliminarPorId(Integer id);
}
