package bg.autohouse.data.repositories;

import bg.autohouse.data.models.Maker;
import bg.autohouse.data.models.Model;
import bg.autohouse.data.projections.ModelProjection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {

  Stream<Model> findAllByMaker(Maker maker);

  @Query("SELECT DISTINCT m FROM Model m LEFT JOIN FETCH m.trims t WHERE m.maker.id = :makerId")
  Stream<Model> findAllByMakerId(Long makerId);

  @Query("SELECT DISTINCT m FROM Model m LEFT JOIN FETCH m.trims t WHERE m.maker.id = :makerId")
  List<ModelProjection> findMakerModelsYears(Long makerId);

  boolean existsByNameAndMakerId(String name, Long makerId);

  boolean existsByIdAndMakerId(Long id, Long makerId);

  Optional<Model> findByIdAndMakerId(Long id, Long makerId);

  Model findByName(String name);
}
