package bg.autohouse.data.models.media;

import bg.autohouse.data.models.BaseUuidEntity;
import bg.autohouse.data.models.EntityConstants;
import javax.persistence.*;
import javax.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
    name = EntityConstants.MEDIA_FILE,
    uniqueConstraints = {
      @UniqueConstraint(
          columnNames = {"bucket", "key"},
          name = "uk_media_file_bucket_key")
    })
public class MediaFile extends BaseUuidEntity {

  private static final long serialVersionUID = 1L;

  @Column(name = "bucket", nullable = false)
  @Setter
  private String bucket;

  @Column(name = "key", nullable = false)
  @Setter
  private String key;

  @Column(name = "thumbnail_url")
  private String thumbnailUrl;

  @Column(nullable = false)
  private String url;
}
