package com.demo.socialsync.Controllers;

import com.demo.socialsync.Models.UsuarioJoin;
import com.demo.socialsync.Repository.UsuarioJoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsuarioJoinController {

    @Autowired
    UsuarioJoinRepository usuarioJoinRepository;

    @GetMapping("/usuariojoinlist")
    public List<UsuarioJoin> obtenerUsuariosJoin(){
        List<UsuarioJoin> listUsuariosJoin = usuarioJoinRepository.listarUsuariosConNombre();
        return listUsuariosJoin;
    }

    @GetMapping("/usuariojoin/{idusuario}")
    public UsuarioJoin obtenerUsuarioJoin(@PathVariable("idusuario") Long idusuario ){
        UsuarioJoin usuariosJoin = usuarioJoinRepository.obtenerUsuarioConNombrePorId(idusuario);
        return usuariosJoin;
    }
}
