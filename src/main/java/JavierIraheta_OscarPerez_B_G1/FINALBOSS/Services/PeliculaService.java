package JavierIraheta_OscarPerez_B_G1.FINALBOSS.Services;

import JavierIraheta_OscarPerez_B_G1.FINALBOSS.Entities.EntityPelicula;
import JavierIraheta_OscarPerez_B_G1.FINALBOSS.Models.DTO.PeliculaDTO;
import JavierIraheta_OscarPerez_B_G1.FINALBOSS.Repositories.RepositoryPelicula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeliculaService {

    @Autowired
    private RepositoryPelicula peliculaRepository;

    private PeliculaDTO convertToDTO(EntityPelicula entity) {
        PeliculaDTO dto = new PeliculaDTO();
        dto.setIdPelicula(entity.getIdPelicula());
        dto.setTitulo(entity.getTitulo());
        dto.setDirector(entity.getDirector());
        dto.setGenero(entity.getGenero());
        dto.setAnioEstreno(entity.getAnioEstreno());
        dto.setDuracionMin(entity.getDuracionMin());
        dto.setFechaCreacion(entity.getFechaCreacion());
        return dto;
    }

    private EntityPelicula convertToEntity(PeliculaDTO dto) {
        EntityPelicula entity = new EntityPelicula();
        // Asigna el ID si está presente en el DTO (útil para actualizaciones)
        if (dto.getIdPelicula() != null) {
            entity.setIdPelicula(dto.getIdPelicula());
        }
        entity.setTitulo(dto.getTitulo());
        entity.setDirector(dto.getDirector());
        entity.setGenero(dto.getGenero());
        entity.setAnioEstreno(dto.getAnioEstreno());
        entity.setDuracionMin(dto.getDuracionMin());
        return entity;
    }

    public List<PeliculaDTO> obtenerTodasLasPeliculas() {
        List<EntityPelicula> entities = peliculaRepository.findAll();
        System.out.println("Service - cantidad de películas encontradas: " + (entities != null ? entities.size() : 0)); // Log para depuración
        for (EntityPelicula e : entities) {
            System.out.println("Entidad: id=" + e.getIdPelicula() + ", titulo=" + e.getTitulo() + ", director=" + e.getDirector() + ", genero=" + e.getGenero() + ", año=" + e.getAnioEstreno() + ", duracion=" + e.getDuracionMin());
        }
        return entities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PeliculaDTO obtenerPeliculaPorId(Long id) {
        EntityPelicula pelicula = peliculaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Película no encontrada con ID: " + id)); 
        return convertToDTO(pelicula);
    }
    
    public PeliculaDTO crearPelicula(PeliculaDTO peliculaDTO) {
        if (peliculaRepository.findByTituloAndAnioEstreno(peliculaDTO.getTitulo(), peliculaDTO.getAnioEstreno()) != null) {
            throw new RuntimeException("Ya existe una película con el mismo título y año de estreno.");
        }
        
        EntityPelicula pelicula = convertToEntity(peliculaDTO);
        pelicula.setFechaCreacion(new Date()); 
        EntityPelicula guardada = peliculaRepository.save(pelicula);
        return convertToDTO(guardada);
    }


    public PeliculaDTO actualizarPelicula(Long id, PeliculaDTO peliculaDTO) {
        

        EntityPelicula peliculaExistente = peliculaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Película no encontrada para actualizar con ID: " + id));


        peliculaExistente.setTitulo(peliculaDTO.getTitulo());
        peliculaExistente.setDirector(peliculaDTO.getDirector());
        peliculaExistente.setGenero(peliculaDTO.getGenero());
        peliculaExistente.setAnioEstreno(peliculaDTO.getAnioEstreno());
        peliculaExistente.setDuracionMin(peliculaDTO.getDuracionMin());
        

        EntityPelicula actualizada = peliculaRepository.save(peliculaExistente);
        return convertToDTO(actualizada);
    }
    
    public void eliminarPelicula(Long id) {
        peliculaRepository.deleteById(id);
    }
}
