package JavierIraheta_OscarPerez_B_G1.FINALBOSS.Controller;

import JavierIraheta_OscarPerez_B_G1.FINALBOSS.Models.DTO.PeliculaDTO;
import JavierIraheta_OscarPerez_B_G1.FINALBOSS.Services.PeliculaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/peliculas")

public class ControllerPelicula {

    @Autowired
    private PeliculaService peliculaService;


    @GetMapping("/getAllPeliculas")
    public ResponseEntity<List<PeliculaDTO>> getAllPeliculas() {
        return ResponseEntity.ok(peliculaService.obtenerTodasLasPeliculas());
    }

    @GetMapping("/getPeliculaById{id}")
    public ResponseEntity<PeliculaDTO> getPeliculaById(@PathVariable Long id) {
        return ResponseEntity.ok(peliculaService.obtenerPeliculaPorId(id));
    }
    
    @PostMapping("/newPelicula")
    public ResponseEntity<PeliculaDTO> createPelicula(@Valid @RequestBody PeliculaDTO  Json, HttpServletRequest request) {
        try{
            PeliculaDTO respuesta = peliculaService.crearPelicula(Json);
            if (respuesta == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Insercion fallida",
                        "errorType", "VALIDATION_ERROR",
                        "message", "Los datos no pudieron ser registrados"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "succes",
                    "data", respuesta
            ));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "Status",
                    "message", "Error no controlado al registrar la pelicula",
                    "detail", e.getMessage()
            ));
        }

    }


    @PutMapping("/updatePelicula{id}")
    public ResponseEntity<PeliculaDTO> updatePelicula(@PathVariable Long id, @RequestBody PeliculaDTO peliculaDTO) {
        PeliculaDTO actualizada = peliculaService.actualizarPelicula(id, peliculaDTO);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/deletePelicula{id}")
    public ResponseEntity<Void> deletePelicula(@PathVariable Long id) {
        peliculaService.eliminarPelicula(id);
        return ResponseEntity.noContent().build(); 
    }

}
