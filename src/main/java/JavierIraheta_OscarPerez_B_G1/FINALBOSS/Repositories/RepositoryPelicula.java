package JavierIraheta_OscarPerez_B_G1.FINALBOSS.Repositories;

import JavierIraheta_OscarPerez_B_G1.FINALBOSS.Entities.EntityPelicula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryPelicula extends JpaRepository<EntityPelicula, Long> {
    EntityPelicula findByTituloAndAnioEstreno(String titulo, Integer anioEstreno);
}
