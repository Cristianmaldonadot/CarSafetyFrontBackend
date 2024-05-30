package com.demo.socialsync.Controllers;

import com.demo.socialsync.Interfaces.IS3Service;
import com.demo.socialsync.Models.*;
import com.demo.socialsync.Repository.SolicitudEnviadaRepository;
import com.demo.socialsync.Repository.SolicitudRecibidaRepository;
import com.demo.socialsync.Repository.UsuarioRepository;
import com.demo.socialsync.Request.CreateUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class UsuarioController {

    @Autowired
    private IS3Service is3Service;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    SolicitudEnviadaRepository solicitudEnviadaRepository;

    @Autowired
    SolicitudRecibidaRepository solicitudRecibidaRepository;

    @GetMapping("/listarUsuarios")
    public List<Usuario> listarUsuarios(){
        List<Usuario> listaUsuarios = (List<Usuario>) usuarioRepository.findAll();
        return listaUsuarios;
    }

    @GetMapping("/listarUsuariosfiltrados/{idusu}")
    public List<Usuario> listarUsuariosFiltrados(@PathVariable("idusu") Long idusu){

        List<Usuario> listaUsuarios = (List<Usuario>) usuarioRepository.findAll();
        Usuario usuarioEntity = usuarioRepository.findByIdUsuario(idusu);

        if(usuarioEntity != null){
            List<Usuario> amigos = usuarioEntity.getAmigos();
            List<SolicitudEnviada> solicitudes = usuarioEntity.getSolicitudesenviadas();

            listaUsuarios.removeIf(usuario -> amigos.contains(usuario));

            List<Usuario> listaUsuSolici = new ArrayList<>();
            for(SolicitudEnviada solici : solicitudes){
                listaUsuSolici.add(solici.getUsuarioReceptor());
            }
            listaUsuarios.removeIf(usuario -> listaUsuSolici.contains(usuario));
            listaUsuarios.removeIf(usuario -> usuario.equals(usuarioEntity));
        }

        return listaUsuarios;
    }

    @PostMapping("/registrarusuario")
    public ResponseEntity<?> crearUsuario(@RequestBody CreateUserDTO usuario){

        Set<RolEntity> roles = usuario.getRoles().stream()
                .map(role -> RolEntity.builder()
                        .name(ERole.valueOf(role))
                        .build())
                .collect(Collectors.toSet());

        String perfildefault = "https://media.istockphoto.com/id/1337144146/vector/default-avatar-profile-icon-vector.jpg?s=612x612&w=0&k=20&c=BIbFwuv7FxTWvh5S3vB6bkT0Qv8Vn8N5Ffseq84ClGI=";

        String portadadefault = "https://images.unsplash.com/photo-1454372182658-c712e4c5a1db?q=80&w=2670&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";

        Usuario userEntity = Usuario.builder()
                .username(usuario.getUsername())
                .password(passwordEncoder.encode(usuario.getPassword()))
                .email(usuario.getEmail())
                .fotoPerfil(usuario.getFotoPerfil() == null ? perfildefault : usuario.getFotoPerfil())
                .fotoPortada(usuario.getFotoPortada() == null ? portadadefault : usuario.getFotoPortada())
                .roles(roles)
                .build();
        usuarioRepository.save(userEntity);

        return ResponseEntity.ok(userEntity);
    }

    @PutMapping("/actualizarusuario/{idusuario}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable("idusuario") Long idusuario){

        Usuario usuarioEntity = usuarioRepository.findByIdUsuario(idusuario);

        Usuario usuarioAdd = Usuario.builder()
                .idUsuario(usuarioEntity.getIdUsuario())
                .username(usuarioEntity.getUsername())
                .email(usuarioEntity.getEmail())
                .fotoPerfil(usuarioEntity.getFotoPerfil())
                .password(usuarioEntity.getPassword())
                .fechaCreacion(usuarioEntity.getFechaCreacion())
                .publicaciones(usuarioEntity.getPublicaciones())
                .roles(usuarioEntity.getRoles())
                .amigos(usuarioEntity.getAmigos())
                .build();

        usuarioRepository.save(usuarioAdd);

        return ResponseEntity.ok(usuarioAdd);

    }

    @GetMapping("/obtenerusuario/{username}")
    public Usuario obtenerUsuarioPorUsername(@PathVariable("username") String username){
        Optional<Usuario> usuarioEntity = usuarioRepository.findByUsername(username);
        return usuarioEntity.orElse(null);
    }

    @GetMapping("/obtenerusuarioid/{idusu}")
    public Usuario obtenerUsuarioPorId(@PathVariable("idusu") Long idusu){
        Optional<Usuario> usuarioEntity = usuarioRepository.findById(idusu);
        return usuarioEntity.orElse(null);
    }

    @PostMapping("/crearAmistad/{usuario1}/{usuario2}/{idsolicitud}")
    public ResponseEntity<?> crearAmistad(@PathVariable("usuario1") Long usuario1, @PathVariable("usuario2") Long usuario2, @PathVariable("idsolicitud") Long idsolicitud ){

        Usuario usuarioEntity1 = usuarioRepository.findByIdUsuario(usuario1);
        Usuario usuarioEntity2 = usuarioRepository.findByIdUsuario(usuario2);
        //SolicitudEnviada solicitudEnviadaEntity = solicitudEnviadaRepository.findByIdSoliciudEnviada(idsolicitud);

        //SolicitudRecibida solicitudRecibidaEntity = solicitudRecibidaRepository.findByIdSoliciudRecibida(idsolicitud);


        // Agregar la relación de amistad
        usuarioEntity1.getAmigos().add(usuarioEntity2);
        usuarioEntity1.getSolicitudesenviadas().clear();

        usuarioEntity2.getAmigos().add(usuarioEntity1);
        usuarioEntity2.getSolicitudesrecibidas().clear();


        // Guardar los cambios en la base de datos
        usuarioRepository.save(usuarioEntity1);
        usuarioRepository.save(usuarioEntity2);

        return ResponseEntity.ok(Map.of("message", "Solicitud aceptado ahora "+usuarioEntity1.getUsername()+ " y " + usuarioEntity2.getUsername() + "son amigos" ));
    }

    @PostMapping("/eliminarAmistad/{usuario1}/{usuario2}")
    public ResponseEntity<?> eliminarAmistad(@PathVariable("usuario1") Long usuario1, @PathVariable("usuario2") Long usuario2) {

        Usuario usuarioEntity1 = usuarioRepository.findByIdUsuario(usuario1);
        Usuario usuarioEntity2 = usuarioRepository.findByIdUsuario(usuario2);

        // Agregar la relación de amistad
        usuarioEntity1.getAmigos().remove(usuarioEntity2);
        usuarioEntity2.getAmigos().remove(usuarioEntity1);

        // Guardar los cambios en la base de datos
        usuarioRepository.save(usuarioEntity1);
        usuarioRepository.save(usuarioEntity2);

        return ResponseEntity.ok("Amistad eliminada correctamente");
    }

    @GetMapping("/{idUsuario}/amigos")
    public ResponseEntity<List<Usuario>> obtenerAmigos(@PathVariable Long idUsuario) {
        List<Usuario> amigos = usuarioRepository.findAmigosByUsuarioId(idUsuario);
        return ResponseEntity.ok(amigos);
    }

    @PostMapping("/crearsolicitud/{usuarioEnviador}/{usuarioReceptor}")
    public ResponseEntity<?> crearSolicitud(@PathVariable("usuarioEnviador") Long usuarioEnviador,
                                            @PathVariable("usuarioReceptor") Long usuarioReceptor){

        Usuario usuarioEntityEnviador = usuarioRepository.findByIdUsuario(usuarioEnviador);
        Usuario usuarioEntityReceptor = usuarioRepository.findByIdUsuario(usuarioReceptor);

        SolicitudEnviada solicitudEnviadaEntity = SolicitudEnviada.builder()
                .usuarioEnviador(usuarioEntityEnviador)
                .usuarioReceptor(usuarioEntityReceptor)
                .estado("Pendiente")
                .build();

        SolicitudRecibida solicitudRecibidaEntity = SolicitudRecibida.builder()
                .usuarioReceptor(usuarioEntityReceptor)
                .usuarioEnviador(usuarioEntityEnviador)
                .estado("Pendiente")
                .build();

        usuarioEntityEnviador.getSolicitudesenviadas().add(solicitudEnviadaEntity);
        usuarioEntityReceptor.getSolicitudesrecibidas().add(solicitudRecibidaEntity);

        usuarioRepository.save(usuarioEntityEnviador);
        usuarioRepository.save(usuarioEntityReceptor);

        return ResponseEntity.ok(Map.of("message", "Solicitudes Enviada a : " + usuarioEntityReceptor.getUsername()));
    }

    @PostMapping("/eliminarSolicitudes/{usuario1}/{usuario2}/{idsolicitud}")
    public ResponseEntity<?> eliminarSolicitudes(@PathVariable("usuario1") Long usuario1, @PathVariable("usuario2") Long usuario2, @PathVariable("idsolicitud") Long idsolicitud ){

        Usuario usuarioEntity1 = usuarioRepository.findByIdUsuario(usuario1);
        Usuario usuarioEntity2 = usuarioRepository.findByIdUsuario(usuario2);
        SolicitudEnviada solicitudEnviadaEntity = solicitudEnviadaRepository.findByIdSoliciudEnviada(idsolicitud);
        SolicitudRecibida solicitudRecibidaEntity = solicitudRecibidaRepository.findByIdSoliciudRecibida(idsolicitud);

        if (usuarioEntity1 == null || usuarioEntity2 == null || solicitudEnviadaEntity == null || solicitudRecibidaEntity == null) {
            return ResponseEntity.badRequest().body("No se pudo encontrar el usuario o la solicitud con los IDs proporcionados.");
        }

        usuarioEntity1.getSolicitudesenviadas().remove(solicitudEnviadaEntity);
        usuarioEntity2.getSolicitudesrecibidas().remove(solicitudRecibidaEntity);

        // Guardar los cambios en la base de datos
        usuarioRepository.save(usuarioEntity1);
        usuarioRepository.save(usuarioEntity2);
        solicitudEnviadaRepository.delete(solicitudEnviadaEntity);
        solicitudRecibidaRepository.delete(solicitudRecibidaEntity);

        return ResponseEntity.ok(Map.of("message", "Solicitud Cancelada" ));
    }

    @PostMapping("/crearAmistadYEliminarSolicitudes/{usuario1}/{usuario2}/{idsolicitud}")
    public ResponseEntity<?> crearAmistadYEliminarSolicitudes(@PathVariable("usuario1") Long usuario1,
                                                              @PathVariable("usuario2") Long usuario2,
                                                              @PathVariable("idsolicitud") Long idsolicitud) {

        Usuario usuarioEntity1 = usuarioRepository.findByIdUsuario(usuario1);
        Usuario usuarioEntity2 = usuarioRepository.findByIdUsuario(usuario2);
        SolicitudEnviada solicitudEnviadaEntity = solicitudEnviadaRepository.findByIdSoliciudEnviada(idsolicitud);
        SolicitudRecibida solicitudRecibidaEntity = solicitudRecibidaRepository.findByIdSoliciudRecibida(idsolicitud);

        if (usuarioEntity1 == null || usuarioEntity2 == null || solicitudEnviadaEntity == null || solicitudRecibidaEntity == null) {
            return ResponseEntity.badRequest().body("No se pudo encontrar el usuario o la solicitud con los IDs proporcionados.");
        }

        // Agregar la relación de amistad
        usuarioEntity1.getAmigos().add(usuarioEntity2);
        usuarioEntity2.getAmigos().add(usuarioEntity1);

        // Eliminar las solicitudes enviadas y recibidas
        usuarioEntity1.getSolicitudesenviadas().remove(solicitudEnviadaEntity);
        usuarioEntity2.getSolicitudesrecibidas().remove(solicitudRecibidaEntity);

        // Guardar los cambios en la base de datos
        usuarioRepository.save(usuarioEntity1);
        usuarioRepository.save(usuarioEntity2);
        solicitudEnviadaRepository.delete(solicitudEnviadaEntity);
        solicitudRecibidaRepository.delete(solicitudRecibidaEntity);

        return ResponseEntity.ok(Map.of("message", "Solicitud aceptada, ahora " + usuarioEntity1.getUsername() + " y " + usuarioEntity2.getUsername() + " son amigos."));
    }

    @PostMapping("/rechazarSolicitudes/{usuario1}/{usuario2}/{idsolicitud}")
    public ResponseEntity<?> rechazarSolicitud(@PathVariable("usuario1") Long usuario1,
                                                              @PathVariable("usuario2") Long usuario2,
                                                              @PathVariable("idsolicitud") Long idsolicitud) {

        Usuario usuarioEntity1 = usuarioRepository.findByIdUsuario(usuario1);
        Usuario usuarioEntity2 = usuarioRepository.findByIdUsuario(usuario2);
        SolicitudEnviada solicitudEnviadaEntity = solicitudEnviadaRepository.findByIdSoliciudEnviada(idsolicitud);
        SolicitudRecibida solicitudRecibidaEntity = solicitudRecibidaRepository.findByIdSoliciudRecibida(idsolicitud);

        if (usuarioEntity1 == null || usuarioEntity2 == null || solicitudEnviadaEntity == null || solicitudRecibidaEntity == null) {
            return ResponseEntity.badRequest().body("No se pudo encontrar el usuario o la solicitud con los IDs proporcionados.");
        }

        // Agregar la relación de amistad
        usuarioEntity1.getAmigos().add(usuarioEntity2);
        usuarioEntity2.getAmigos().add(usuarioEntity1);

        // Eliminar las solicitudes enviadas y recibidas
        usuarioEntity1.getSolicitudesenviadas().remove(solicitudEnviadaEntity);
        usuarioEntity2.getSolicitudesrecibidas().remove(solicitudRecibidaEntity);

        // Guardar los cambios en la base de datos
        usuarioRepository.save(usuarioEntity1);
        usuarioRepository.save(usuarioEntity2);
        solicitudEnviadaRepository.delete(solicitudEnviadaEntity);
        solicitudRecibidaRepository.delete(solicitudRecibidaEntity);

        return ResponseEntity.ok(Map.of("message", "Solicitud Rechazada"));
    }

    @PostMapping("/guardarportada")
    public ResponseEntity<?> guardarPortada(@RequestParam Long idusuario, @RequestParam(name = "file")MultipartFile file) throws IOException {
        String urlCompleta;
        Usuario usuarioEntity = usuarioRepository.findByIdUsuario(idusuario);

        String oldurl = usuarioEntity.getFotoPortada();
        int lastSlashIndex = oldurl.lastIndexOf('/');
        String fileName = oldurl.substring(lastSlashIndex + 1);
        is3Service.deleteFile(fileName);

        String nombreArchivo = generateRandomLetters()+generarNumeroAleatorio()+"socialsync_portada.jpg";
        urlCompleta = "http://imagenes-tienda.s3.us-east-2.amazonaws.com/"+nombreArchivo;

        usuarioEntity.setFotoPortada(urlCompleta);
        is3Service.uploadFile(file, nombreArchivo);

        usuarioRepository.save(usuarioEntity);

        return ResponseEntity.ok(usuarioEntity);
    }

    @PostMapping("/guardarperfil")
    public ResponseEntity<?> guardarPerfil(@RequestParam Long idusuario, @RequestParam(name = "file")MultipartFile file) throws IOException {
        String urlCompleta;
        Usuario usuarioEntity = usuarioRepository.findByIdUsuario(idusuario);

        String oldurl = usuarioEntity.getFotoPerfil();
        int lastSlashIndex = oldurl.lastIndexOf('/');
        String fileName = oldurl.substring(lastSlashIndex + 1);
        is3Service.deleteFile(fileName);

        String nombreArchivo = generateRandomLetters()+generarNumeroAleatorio()+file.getOriginalFilename();
        urlCompleta = "http://imagenes-tienda.s3.us-east-2.amazonaws.com/"+nombreArchivo;
        usuarioEntity.setFotoPerfil(urlCompleta);

        is3Service.uploadFilePerfil(file, nombreArchivo);

        usuarioRepository.save(usuarioEntity);

        return ResponseEntity.ok(usuarioEntity);
    }

    public int generarNumeroAleatorio(){
        Random random = new Random();

        // Generar un número aleatorio de 5 dígitos
        int min = 10000;
        int max = 99999;
        int randomFiveDigit = random.nextInt((max - min) + 1) + min;
        return randomFiveDigit;
    }

    public String generateRandomLetters() {
        Random random = new Random();
        StringBuilder letters = new StringBuilder(5);

        for (int i = 0; i < 5; i++) {
            char randomLetter = (char) ('A' + random.nextInt(26)); // Genera una letra mayúscula aleatoria
            letters.append(randomLetter);
        }

        return letters.toString();
    }

}
