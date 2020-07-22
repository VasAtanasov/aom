package bg.autohouse.data.repositories;

import bg.autohouse.data.models.Filter;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterRepository extends JpaRepository<Filter, UUID> {

  List<Filter> findAllByUserId(UUID userId);
}
