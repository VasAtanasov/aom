package bg.autohouse.data.repositories;

import bg.autohouse.data.models.geo.Province;
import bg.autohouse.data.projections.geo.ProvinceIdName;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
  @Query("SELECT p from Province p")
  List<ProvinceIdName> getAllLocationIds();
}
