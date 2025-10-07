package JavierIraheta_OscarPerez_B_G1.FINALBOSS.Models.DTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class PeliculaDTO {
    private Long idPelicula;

    private String titulo;

    private String director;

    private String genero;

    private Number anioEstreno;

    private Number duracionMin;

    private Date fechaCreacion;
}
