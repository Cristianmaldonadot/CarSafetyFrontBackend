package com.demo.socialsync.Controllers;

import com.demo.socialsync.Models.Megusta;
import com.demo.socialsync.Repository.MegustaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MegustaController {

    @Autowired
    MegustaRepository megustaRepository;

    @PostMapping("/crearMegusta")
    public ResponseEntity<?> crearMegusta(@RequestBody Megusta megusta){

        Megusta megustaEntity = Megusta.builder()
                .publicacion(megusta.getPublicacion())
                .usuario(megusta.getUsuario())
                .build();
        megustaRepository.save(megustaEntity);

        return ResponseEntity.ok(megustaEntity);
    }
    @DeleteMapping("/deleteMegusta/{idMegusta}")
    public ResponseEntity<?> eliminarMegusta(@PathVariable("idMegusta") int idMegusta){
        megustaRepository.eliminarPorId(idMegusta);
        System.out.println(idMegusta);
        return ResponseEntity.ok(idMegusta);
    }
}
