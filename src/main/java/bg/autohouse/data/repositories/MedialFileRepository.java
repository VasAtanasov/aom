package bg.autohouse.data.repositories;

import bg.autohouse.data.models.media.MediaFile;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MedialFileRepository extends JpaRepository<MediaFile, UUID> {

  List<MediaFile> findAllByReferenceId(UUID referenceId);

  Optional<MediaFile> findByBucketAndFileKey(String bucket, String key);

  List<MediaFile> findByBucketAndFileKeyIn(String bucket, List<String> keys);

  boolean existsByBucketAndFileKey(String bucket, String key);

  @Transactional
  @Modifying(clearAutomatically = true, flushAutomatically = true)
  void deleteAllByReferenceId(UUID referenceId);
}
