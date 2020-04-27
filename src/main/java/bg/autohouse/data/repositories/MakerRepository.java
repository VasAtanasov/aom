package bg.autohouse.data.repositories;

import bg.autohouse.data.models.Maker;
import bg.autohouse.data.models.Model;
import bg.autohouse.util.Collect;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MakerRepository extends JpaRepository<Maker, Long> {

  @Query("SELECT DISTINCT m FROM Maker m LEFT JOIN FETCH m.models mo WHERE m.name = :name")
  Optional<Maker> findByName(String name);

  @Override
  @Query("SELECT DISTINCT m FROM Maker m LEFT JOIN FETCH m.models mo WHERE m.id = :id")
  Optional<Maker> findById(Long id);

  @Query("SELECT DISTINCT m FROM Maker m LEFT JOIN FETCH m.models mo WHERE m.id = :id")
  Optional<Maker> findByIdWithModelsTrims(Long id);

  @Query("SELECT DISTINCT m FROM Maker m LEFT JOIN FETCH m.models mo")
  List<Maker> findAllWithModels();

  @Query("SELECT DISTINCT m FROM Maker m JOIN FETCH m.models mo JOIN FETCH mo.trims tr")
  Stream<Maker> makerModelsStream();

  boolean existsByName(String name);

  default Map<Long, List<Model>> modelsByMaker() {
    try (Stream<Maker> makerStream = makerModelsStream()) {
      return makerStream.collect(
          Collect.toMap(maker -> maker.getId(), maker -> maker.getModels(), LinkedHashMap::new));
    }
  }
}
