package com.demo.socialsync.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "solicitudenviada")
public class SolicitudEnviada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuarioEnviador", nullable = true)
    @JsonIgnoreProperties({"password", "email", "nombre", "apellido", "fechaNacimiento", "fechaCreacion", "biografia", "fotoPortada", "publicaciones", "roles", "amigos", "solicitudesenviadas", "solicitudesrecibidas"})
    private Usuario usuarioEnviador;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuarioReceptor", nullable = true)
    @JsonIgnoreProperties({"password", "email", "nombre", "apellido", "fechaNacimiento", "fechaCreacion", "biografia", "fotoPortada", "publicaciones", "roles", "amigos", "solicitudesenviadas", "solicitudesrecibidas"})
    private Usuario usuarioReceptor;

    private LocalDateTime fechaSolicitudEnviada;
    private String estado;

    @PrePersist
    protected void onCreate() {
        fechaSolicitudEnviada = LocalDateTime.now();
    }
}
