package JavierIraheta_OscarPerez_B_G1.FINALBOSS.Controller;

import JavierIraheta_OscarPerez_B_G1.FINALBOSS.Models.DTO.PeliculaDTO;
import JavierIraheta_OscarPerez_B_G1.FINALBOSS.Services.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<PeliculaDTO> createPelicula(@RequestBody PeliculaDTO peliculaDTO) {
        PeliculaDTO nuevaPelicula = peliculaService.crearPelicula(peliculaDTO);
        return new ResponseEntity<>(nuevaPelicula, HttpStatus.CREATED);
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
