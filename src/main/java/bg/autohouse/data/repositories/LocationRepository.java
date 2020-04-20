package bg.autohouse.data.repositories;

import bg.autohouse.data.models.geo.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {}
