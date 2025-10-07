package JavierIraheta_OscarPerez_B_G1.FINALBOSS.Controller;

import JavierIraheta_OscarPerez_B_G1.FINALBOSS.Models.DTO.PeliculaDTO;
import JavierIraheta_OscarPerez_B_G1.FINALBOSS.Services.PeliculaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/peliculas")
public class ControllerPelicula {

    @Autowired
    private PeliculaService peliculaService;

    // Métodos GET (sin cambios en la estructura de errores)
    @GetMapping("/getAllPeliculas")
    public ResponseEntity<List<PeliculaDTO>> getAllPeliculas() {
        return ResponseEntity.ok(peliculaService.obtenerTodasLasPeliculas());
    }

    @GetMapping("/getPeliculaById/{id}")
    public ResponseEntity<PeliculaDTO> getPeliculaById(@PathVariable Long id) {
        return ResponseEntity.ok(peliculaService.obtenerPeliculaPorId(id));
    }
    
    // Método POST con manejo de errores extendido (similar a tu imagen de 'RegistrarDatos')
    @PostMapping("/newPelicula")
    public ResponseEntity<Map<String, Object>> createPelicula(@Valid @RequestBody PeliculaDTO Json, BindingResult bindingResult, HttpServletRequest request) {
        
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> 
                errors.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(Map.of("Errors", (Object)errors));
        }
        
        try {
            PeliculaDTO respuesta = peliculaService.crearPelicula(Json);
            
            if (respuesta == null) {
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
        } catch (RuntimeException e) {
            // Manejo de la excepción de duplicidad de la lógica de negocio
            if (e.getMessage().contains("Ya existe una película")) {
                 return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "error", "Datos Duplicados",
                    "message", e.getMessage()
                ));
            }
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "Error",
                    "message", "Error no controlado al registrar la pelicula",
                    "detail", e.getMessage()
            ));
        }
    }


    // Método PUT modificado (similar a tu imagen de 'editarUsuario')
    @PutMapping("/updatePelicula/{id}")
    public ResponseEntity<Map<String, Object>> updatePelicula(@PathVariable Long id, @Valid @RequestBody PeliculaDTO Json, BindingResult bindingResult) {
        
        // 1. Manejo de errores de validación de DTO (BindingResult)
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> 
                errors.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(Map.of("Errors", (Object)errors));
        }

        try {
            // Llama al servicio para actualizar
            PeliculaDTO actualizada = peliculaService.actualizarPelicula(id, Json);
            
            // Éxito
            return ResponseEntity.ok(Map.of(
                "status", "success", 
                "data", actualizada
            ));

        } catch (RuntimeException e) {
            // 2. Manejo de excepciones de negocio
            if (e.getMessage().contains("no encontrada")) {
                return ResponseEntity.notFound().build();
            }
            // Puedes agregar aquí el manejo de "Datos Duplicados" si aplica al update
            if (e.getMessage().contains("Ya existe una película")) {
                 return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "error", "Datos Duplicados",
                    "message", e.getMessage()
                ));
            }
            
            // 3. Manejo de error interno
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "status", "Error",
                "message", "Error no controlado al actualizar la película",
                "detail", e.getMessage()
            ));
        }
    }

    // Método DELETE (sin cambios en la estructura de errores)
    @DeleteMapping("/deletePelicula/{id}")
    public ResponseEntity<Void> deletePelicula(@PathVariable Long id) {
        peliculaService.eliminarPelicula(id);
        return ResponseEntity.noContent().build(); 
    }
}
