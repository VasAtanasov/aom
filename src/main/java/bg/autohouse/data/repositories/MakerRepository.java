package bg.autohouse.data.repositories;

import bg.autohouse.data.models.Maker;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MakerRepository extends JpaRepository<Maker, Long> {
  @Query("SELECT DISTINCT m FROM Maker m LEFT JOIN FETCH m.models mo WHERE m.name = :name")
  Optional<Maker> findByName(String name);
}
