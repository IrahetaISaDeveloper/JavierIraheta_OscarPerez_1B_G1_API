package JavierIraheta_OscarPerez_B_G1.FINALBOSS.Services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;

    private PeliculaDTO toDTO(Pelicula entity) {
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

    private Pelicula toEntity(PeliculaDTO dto) {
        Pelicula entity = new Pelicula();
        entity.setTitulo(dto.getTitulo());
        entity.setDirector(dto.getDirector());
        entity.setGenero(dto.getGenero());
        entity.setAnioEstreno(dto.getAnioEstreno());
        entity.setDuracionMin(dto.getDuracionMin());
        return entity;
    }


    public List<PeliculaDTO> obtenerTodasLasPeliculas() {
        return peliculaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PeliculaDTO obtenerPeliculaPorId(Long id) {
        Pelicula pelicula = peliculaRepository.findById(id)

                .orElseThrow(() -> new RuntimeException("Película no encontrada con ID: " + id)); 
        return toDTO(pelicula);
    }
    
    public PeliculaDTO crearPelicula(PeliculaDTO peliculaDTO) {
        // Lógica de validación de unicidad (UQ_PELICULAS_TITULO_ANO)
        if (peliculaRepository.findByTituloAndAnioEstreno(peliculaDTO.getTitulo(), peliculaDTO.getAnioEstreno()) != null) {
            throw new RuntimeException("Ya existe una película con el mismo título y año de estreno.");
        }
        
        Pelicula pelicula = toEntity(peliculaDTO);
        pelicula.setFechaCreacion(new Date()); // Establecer la fecha de registro
        Pelicula guardada = peliculaRepository.save(pelicula);
        return toDTO(guardada);
    }

    public PeliculaDTO actualizarPelicula(Long id, PeliculaDTO peliculaDTO) {
        Pelicula peliculaExistente = peliculaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Película no encontrada para actualizar con ID: " + id));

        peliculaExistente.setTitulo(peliculaDTO.getTitulo());
        peliculaExistente.setDirector(peliculaDTO.getDirector());
        peliculaExistente.setGenero(peliculaDTO.getGenero());
        peliculaExistente.setAnioEstreno(peliculaDTO.getAnioEstreno());
        peliculaExistente.setDuracionMin(peliculaDTO.getDuracionMin());
        
        Pelicula actualizada = peliculaRepository.save(peliculaExistente);
        return toDTO(actualizada);
    }
    
    public void eliminarPelicula(Long id) {
        peliculaRepository.deleteById(id);
    }

}
