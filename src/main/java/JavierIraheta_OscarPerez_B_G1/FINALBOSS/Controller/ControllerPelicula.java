package JavierIraheta_OscarPerez_B_G1.FINALBOSS.Controller;

import java.util.List;

@RestController
@RequestMapping("/api/peliculas")

public class ControllerPelicula {

    @Autowired
    private PeliculaService peliculaService;


    @GetMapping
    public ResponseEntity<List<PeliculaDTO>> getAllPeliculas() {
        return ResponseEntity.ok(peliculaService.obtenerTodasLasPeliculas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeliculaDTO> getPeliculaById(@PathVariable Long id) {
        return ResponseEntity.ok(peliculaService.obtenerPeliculaPorId(id));
    }
    
    @PostMapping
    public ResponseEntity<PeliculaDTO> createPelicula(@RequestBody PeliculaDTO peliculaDTO) {
        PeliculaDTO nuevaPelicula = peliculaService.crearPelicula(peliculaDTO);
        return new ResponseEntity<>(nuevaPelicula, HttpStatus.CREATED); 
    }


    @PutMapping("/{id}")
    public ResponseEntity<PeliculaDTO> updatePelicula(@PathVariable Long id, @RequestBody PeliculaDTO peliculaDTO) {
        PeliculaDTO actualizada = peliculaService.actualizarPelicula(id, peliculaDTO);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePelicula(@PathVariable Long id) {
        peliculaService.eliminarPelicula(id);
        return ResponseEntity.noContent().build(); 
    }

}
