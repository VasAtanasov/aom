package bg.autohouse.data.models.media;

import bg.autohouse.data.models.BaseUuidEntity;
import bg.autohouse.data.models.EntityConstants;
import bg.autohouse.data.models.Offer;
import javax.persistence.*;
import javax.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = EntityConstants.IMAGES)
@Builder
public class Image extends BaseUuidEntity {

  private static final long serialVersionUID = 1L;

  @Column(name = "thumbnail_url")
  private String thumbnailUrl;

  @Column(nullable = false)
  private String url;

  @ManyToOne(targetEntity = Offer.class)
  @JoinColumn(name = "offer_id", referencedColumnName = "id", nullable = false)
  private Offer offer;
}
