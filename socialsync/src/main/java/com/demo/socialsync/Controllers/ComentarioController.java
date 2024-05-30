package com.demo.socialsync.Controllers;

import com.demo.socialsync.Models.Comentario;
import com.demo.socialsync.Models.Publicacion;
import com.demo.socialsync.Models.Usuario;
import com.demo.socialsync.Repository.ComentarioRepository;
import com.demo.socialsync.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ComentarioController {

    @Autowired
    ComentarioRepository comentarioRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/listarComentarios")
    public List<Comentario> listarComentarios(){
        List<Comentario> lstComentario = comentarioRepository.findAll();
        return lstComentario;
    }

    @PostMapping("/crearComentario")
    public ResponseEntity<?> crearComentario(@RequestBody Comentario comentario){

        Comentario comentarioEntity = Comentario.builder()
                .publicacion(comentario.getPublicacion())
                .usuario(comentario.getUsuario())
                .contenido(comentario.getContenido())
                .build();
        comentarioRepository.save(comentarioEntity);

        return ResponseEntity.ok(comentarioEntity);
    }
}
