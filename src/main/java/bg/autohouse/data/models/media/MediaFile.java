package bg.autohouse.data.models.media;

import bg.autohouse.data.models.BaseUuidEntity;
import bg.autohouse.data.models.EntityConstants;
import java.net.URL;
import java.util.UUID;
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
    indexes = {
      @Index(
          name = "idx_" + EntityConstants.MEDIA_FILE + "_reference_id",
          columnList = "reference_id"),
      @Index(
          name = "idx_" + EntityConstants.MEDIA_FILE + "_storage_type",
          columnList = "storage_type")
    },
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

  @Column(name = "original_file_name")
  private String originalFilename;

  @Column(name = "mime_type")
  private String contentType;

  @Column(name = "content_hash")
  private String contentHash;

  @Column(name = "size")
  private long size;

  @Column(name = "resource_url")
  private URL resourceUrl;

  @Column(name = "reference_id", columnDefinition = "BINARY(16)")
  private UUID referenceId;
}
