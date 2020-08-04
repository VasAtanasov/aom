package bg.autohouse.data.repositories;

import bg.autohouse.data.models.geo.Province;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
  @Query("SELECT DISTINCT p FROM Province p LEFT JOIN FETCH p.locations l")
  List<Province> getAllLocations();

  @Query("SELECT DISTINCT p FROM Province p LEFT JOIN FETCH p.locations l WHERE p.id = :id")
  Province findProvinceById(Long id);
}
