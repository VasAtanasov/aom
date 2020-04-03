package bg.autohouse.data.repositories;

import bg.autohouse.data.models.Dealership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealershipRepository extends JpaRepository<Dealership, String> {
  boolean existsByNameIgnoreCase(String name);
}
