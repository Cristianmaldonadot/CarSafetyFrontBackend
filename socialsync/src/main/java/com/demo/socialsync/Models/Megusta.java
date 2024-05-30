package com.demo.socialsync.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "megusta")
public class Megusta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUsuario")
    @JsonIgnoreProperties({"password", "email", "nombre", "apellido", "fechaNacimiento", "fechaCreacion", "biografia","fotoPerfil", "fotoPortada", "publicaciones", "roles", "amigos", "solicitudesenviadas", "solicitudesrecibidas"})
    private Usuario usuario;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "publicacion")
    private Publicacion publicacion;
    private LocalDateTime fechaLike;

    @PrePersist
    protected void onCreate() {
        fechaLike = LocalDateTime.now();
    }
}
