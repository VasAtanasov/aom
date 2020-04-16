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

  @Enumerated(EnumType.STRING)
  @Column(name = "storage_type", nullable = false)
  private StorageType storageType;

  @Column(name = "bucket", nullable = false)
  private String bucket;

  @Column(name = "file_key", nullable = false)
  private String fileKey;

  @Column(name = "file_name")
  private String fileName;

  @Column(name = "mime_type")
  private String contentType;

  @Column(name = "content_hash")
  private String contentHash;

  @Column(name = "size")
  private long size;
}
