package bg.autohouse.data.repositories;

import bg.autohouse.data.models.Maker;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MakerRepository extends JpaRepository<Maker, Long> {
  Optional<Maker> findByName(String name);

  boolean existsByName(String name);
}
