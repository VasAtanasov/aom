package bg.autohouse.data.models;

import bg.autohouse.data.models.media.MediaFile;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = EntityConstants.FILE_CONTENT)
public class PersistentFileContent extends BaseUuidEntity {

  private static final long serialVersionUID = 3430927816455034200L;

  @MapsId
  @NotNull
  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "media_file_id", unique = true, nullable = false, updatable = false)
  private MediaFile mediaFile;

  @NotNull
  @Column(name = "file_content", nullable = false)
  private byte[] content;
}
