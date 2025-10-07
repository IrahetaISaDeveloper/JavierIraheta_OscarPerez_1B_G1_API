package JavierIraheta_OscarPerez_B_G1.FINALBOSS.Entities;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "ADMIN.PELICULAS")
public class EntityPelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PELICULA")
    private Long idPelicula;

    @Column(name = "TITULO", length = 200, nullable = false)
    private String titulo;

    @Column(name = "DIRECTOR", length = 120)
    private String director;

    @Column(name = "GENERO", length = 120)
    private String genero;

    @Column(name = "ANO_ESTRENO")
    private Number anioEstreno;

    @Column(name = "DURACION_MIN")
    private Number duracionMin;

    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    

}
