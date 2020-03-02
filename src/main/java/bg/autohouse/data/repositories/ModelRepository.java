package bg.autohouse.data.repositories;

import bg.autohouse.data.models.Model;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
  Stream<Model> findAllByMakerId(Long makerId);
}
