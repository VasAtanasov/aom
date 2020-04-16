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
          columnNames = {"bucket", "file_key"},
          name = "uk_media_file_bucket_file_key")
    })
public class MediaFile extends BaseUuidEntity {

  private static final long serialVersionUID = 1L;

  @Column(name = "bucket", nullable = false)
  private String bucket;

  @Column(name = "file_key", nullable = false)
  private String fileKey;

  @Column(name = "file_name")
  private String fileName;

  @Column(name = "mime_type")
  private String mimeType;

  private String rev;

  private String contentHash;

  private long size;
}
