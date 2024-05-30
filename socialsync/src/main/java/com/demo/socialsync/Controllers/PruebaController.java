package com.demo.socialsync.Controllers;

import com.demo.socialsync.Models.Prueba;
import com.demo.socialsync.Repository.PruebaRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PruebaController {

    @Autowired
    PruebaRespository pruebaRespository;

    @PostMapping("/creaprueba")
    public ResponseEntity<?> crearPrueba(@RequestBody Prueba prueba){

        Prueba pruebaEntity = Prueba.builder()
                .nombre(prueba.getNombre())
                .apellido(prueba.getApellido())
                .build();
        pruebaRespository.save(pruebaEntity);

        return ResponseEntity.ok(pruebaEntity);
    }

    @DeleteMapping("/eliminarprueba/{id}")
    public ResponseEntity<?> eliminarPrueba(@PathVariable("id") int id){
        pruebaRespository.deleteById(id);
        return ResponseEntity.ok(id);
    }
}
