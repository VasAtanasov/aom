package bg.autohouse.data.repositories;

import bg.autohouse.data.models.Trim;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrimRepository extends JpaRepository<Trim, Long> {
  Stream<Trim> findAllByModelId(Long modelId);
}
