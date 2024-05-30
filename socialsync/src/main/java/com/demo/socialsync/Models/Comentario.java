package com.demo.socialsync.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "comentario")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idComentario;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "publicacion", nullable = true)
    private Publicacion publicacion;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    @JsonIgnoreProperties({"password", "email", "nombre", "apellido", "fechaNacimiento", "fechaCreacion", "biografia", "fotoPortada", "publicaciones", "roles", "amigos", "solicitudesenviadas", "solicitudesrecibidas"})
    private Usuario usuario;
    private String contenido;
    private LocalDateTime fechaComentario;

    @Transient
    private String tiempoTranscurrido;

    public String getTiempoTranscurrido() {
        if (fechaComentario != null) {
            LocalDateTime fechaActual = LocalDateTime.now();
            Duration diferencia = Duration.between(fechaComentario, fechaActual);
            long horas = diferencia.toHours();
            long minutos = diferencia.toMinutesPart();
            return String.format("%dh %02dm", horas, minutos);
        } else {
            return null; // Manejo de casos donde fechaPublicacion es null
        }
    }

    @PrePersist
    protected void onCreate() {
        fechaComentario = LocalDateTime.now();
    }
}
