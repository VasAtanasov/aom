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

  @Column(name = "file_name")
  private String fileName;

  @Column(name = "mime_type")
  private String mimeType;

  @Column(name = "thumbnail_url")
  private String thumbnailUrl;

  @Column(nullable = false)
  private String url;

  @Builder
  public MediaFile(String bucket, String contentType, String key, String fileName) {
    this.bucket = bucket;
    this.key = key;
    this.mimeType = contentType;
    this.fileName = fileName;
  }
}
