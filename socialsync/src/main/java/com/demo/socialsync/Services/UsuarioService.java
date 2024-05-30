package com.demo.socialsync.Services;

import com.demo.socialsync.Models.Usuario;
import com.demo.socialsync.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public Usuario findByNombre(String username) {
        return usuarioRepository.findByUsername(username).orElse(null);
    }


}
