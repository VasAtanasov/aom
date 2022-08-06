package bg.autohouse.core.domain.repositories;

import bg.autohouse.core.domain.model.Maker;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MakerRepository extends JpaRepository<Maker, Long> {
  @Query(
      "SELECT DISTINCT "
          + "m FROM Maker m "
          + "LEFT JOIN FETCH m.models mo "
          + "WHERE m.name = :name")
  Optional<Maker> findByName(String name);

  @Query("SELECT DISTINCT m FROM Maker m LEFT JOIN FETCH m.models mo WHERE m.id = :id")
  Optional<Maker> findMakerById(Long id);

  @Query("SELECT DISTINCT m FROM Maker m LEFT JOIN FETCH m.models mo")
  List<Maker> findAllWithModels();

  boolean existsByName(String name);
}
