package bg.autohouse.data.repositories;

import bg.autohouse.data.models.media.MediaFile;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedialFileRepository extends JpaRepository<MediaFile, String> {

  List<MediaFile> findByIdIn(List<String> uids);

  MediaFile findByBucketAndKey(String bucket, String key);

  List<MediaFile> findByBucketAndKeyIn(String bucket, List<String> keys);
}
