package com.demo.socialsync.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    @NotBlank
    @Size(max = 40)
    @Column(unique = true)
    private String username;
    @NotBlank
    private String password;
    @Email
    @NotBlank
    @Size(max = 60)
    private String email;
    private String nombre;
    private String apellido;
    private Date fechaNacimiento;
    private LocalDateTime fechaCreacion;
    private String biografia;
    private String fotoPerfil;
    private String fotoPortada;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Publicacion> publicaciones;
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = RolEntity.class, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RolEntity> roles;
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Usuario.class, cascade = CascadeType.ALL)
    @JoinTable(
            name = "Amistades",
            joinColumns = @JoinColumn(name = "idUsuario"),
            inverseJoinColumns = @JoinColumn(name = "amigo_id")
    )
    @JsonIgnoreProperties({"password", "email", "nombre", "apellido", "fechaNacimiento", "fechaCreacion", "fotoPortada", "biografia", "publicaciones", "roles", "amigos"})
    private List<Usuario> amigos;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = SolicitudEnviada.class, cascade = CascadeType.ALL)
    @JoinTable(
            name = "usuario_solicitudenviada",
            joinColumns = @JoinColumn(name = "usuarioEnviador"),
            inverseJoinColumns = @JoinColumn(name = "solicitudEnviada")
    )
    private List<SolicitudEnviada> solicitudesenviadas;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = SolicitudRecibida.class, cascade = CascadeType.ALL)
    @JoinTable(
            name = "usuario_solicitudrecibida",
            joinColumns = @JoinColumn(name = "usuarioReceptor"),
            inverseJoinColumns = @JoinColumn(name = "solicitudRecibida")
    )
    private List<SolicitudRecibida> solicitudesrecibidas;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }

    public Usuario(Long idUsuario, String username) {
        this.idUsuario = idUsuario;
        this.username = username;
    }

}
