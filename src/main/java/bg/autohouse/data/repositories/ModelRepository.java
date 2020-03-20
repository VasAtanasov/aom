package bg.autohouse.data.repositories;

import bg.autohouse.data.models.Maker;
import bg.autohouse.data.models.Model;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
  Stream<Model> findAllByMaker(Maker maker);

  Stream<Model> findAllByMakerId(Long makerId);

  boolean existsByNameAndMakerId(String name, Long makerId);

  boolean existsByIdAndMakerId(Long id, Long makerId);

  Optional<Model> findByIdAndMakerId(Long id, Long makerId);

  Model findByName(String name);
}
