package com.demo.socialsync.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "publicacion")
public class Publicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    @JsonIgnoreProperties({"password", "email", "nombre", "apellido", "fechaNacimiento", "fechaCreacion", "fotoPortada", "biografia", "publicaciones", "roles", "amigos", "solicitudesenviadas", "solicitudesrecibidas"})
    private Usuario usuario;
    private String titulo;
    private String imagen;
    private LocalDateTime fechaPublicacion;
    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comentario> comentarios;
    @JsonManagedReference
    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Megusta> megustas;

    @Transient
    private String tiempoTranscurrido;

    public String getTiempoTranscurrido() {
        if (fechaPublicacion != null) {
            LocalDateTime fechaActual = LocalDateTime.now();
            Duration diferencia = Duration.between(fechaPublicacion, fechaActual);
            long dias = diferencia.toDays();
            long horas = diferencia.toHours();
            long minutos = diferencia.toMinutes();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy hh:mm a", new Locale("es", "ES"));
            if(dias > 7){
                return fechaPublicacion.format(formatter);
            }
            else if(horas > 24){
                return String.format("%dd", dias);
            }else if(minutos > 60){
                return String.format("%dh", horas);
            }else{
                return String.format("%02dm", minutos);
            }
        } else {
            return null; // Manejo de casos donde fechaPublicacion es null
        }
    }

    @PrePersist
    protected void onCreate() {
        fechaPublicacion = LocalDateTime.now();
    }
}
