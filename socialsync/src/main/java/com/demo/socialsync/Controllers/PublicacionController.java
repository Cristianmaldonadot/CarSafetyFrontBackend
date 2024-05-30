package com.demo.socialsync.Controllers;

import com.demo.socialsync.Interfaces.IS3Service;
import com.demo.socialsync.Models.Publicacion;
import com.demo.socialsync.Models.Usuario;
import com.demo.socialsync.Repository.PublicacionRepository;
import com.demo.socialsync.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class PublicacionController {

    @Autowired
    private IS3Service is3Service;

    @Autowired
    PublicacionRepository publicacionRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioController usuarioController;

    @GetMapping("/listarPublicaciones")
    public List<Publicacion> listarPublicaciones(){
        List<Publicacion> lstpublicaciones = publicacionRepository.findAll();
        return lstpublicaciones;
    }
    @GetMapping("/listarPublicacionesUsuario/{idsuario}")
    public List<Publicacion> listarPublicacionesPorUsuario(@PathVariable("idsuario") Long idsuario ){

        Usuario usuarioEntity = usuarioRepository.findByIdUsuario(idsuario);

        List<Publicacion> lstpublicaciones = publicacionRepository.findByUsuario(usuarioEntity);

        return lstpublicaciones;
    }

    @PostMapping("/crearPublicacion")
    public ResponseEntity<?> crearPublicacion(@RequestParam Long idusuario, @RequestParam(name = "file") MultipartFile file,
                                              @RequestParam String titulo)throws IOException {

        String urlCompleta;
        String name = "socialsync_publicacion.jpg";
        Usuario usuarioEntity = usuarioRepository.findByIdUsuario(idusuario);
        String nombreArchivo = usuarioController.generateRandomLetters()+usuarioController.generarNumeroAleatorio()+"socialsync_publicacion.jpg";
        urlCompleta = "http://imagenes-tienda.s3.us-east-2.amazonaws.com/"+nombreArchivo;

        Publicacion publicaconEntity = Publicacion.builder()
                .usuario(usuarioEntity)
                .titulo(titulo)
                .imagen(urlCompleta)
                .build();
        is3Service.uploadFile(file, name);

        publicacionRepository.save(publicaconEntity);

        return ResponseEntity.ok(Map.of("message", "Publicacion Creada"));

    }
    @DeleteMapping("/eliminarpublicacion/{idpubli}")
    public ResponseEntity<?> eliminarPublicacion(@PathVariable("idpubli") int idpubli) throws IOException {

        Publicacion publicacionEntity = publicacionRepository.findById(idpubli).orElse(null);

        String oldurl = publicacionEntity.getImagen();
        int lastSlashIndex = oldurl.lastIndexOf('/');
        String fileName = oldurl.substring(lastSlashIndex + 1);
        is3Service.deleteFile(fileName);

        publicacionRepository.deleteById(idpubli);
        return ResponseEntity.ok(Map.of("message", "Publicacion eliminada satisfactoriamente"));
    }
}
