package bg.autohouse.data.repositories;

import bg.autohouse.data.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {

    List<Image> findByOfferId(String id);
}
