package bg.autohouse.data.repositories;

import bg.autohouse.data.models.geo.Location;
import bg.autohouse.data.projections.geo.LocationId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LocationRepository extends JpaRepository<Location, Long> {
  @Query("SELECT lo from Location lo")
  List<LocationId> getAllLocationIds();

  Optional<Location> findFirstByPostalCode(Integer postalCode);

  @Query("SELECT lo from Location lo WHERE lo.postalCode > 0")
  List<Location> findAllLocations();
}
