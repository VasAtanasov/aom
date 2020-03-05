package bg.autohouse.data.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "offers")
public class Offer extends BaseUuidEntity implements EntityDetails {

  private static final long serialVersionUID = -2840866963962522737L;

  @Column(name = "thumbnail", nullable = false)
  private String thumbnail;

  @OneToMany(
      mappedBy = "offer",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  private List<Image> images = new ArrayList<>();

  @Column(name = "price", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT(0)")
  private Integer price;

  @Temporal(TemporalType.TIMESTAMP)
  @LastModifiedDate
  @Column(name = "price_modified_on")
  private Date priceModifiedOn;

  @ManyToOne(targetEntity = Location.class)
  @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
  private Location location;

  @Column(name = "hit_count", columnDefinition = "INT UNSIGNED DEFAULT(0)")
  private Integer hitCount = 0;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "is_active")
  private boolean isActive = true;

  @Column(name = "is_deleted")
  private boolean isDeleted = false;

  @Column(name = "is_expired")
  private boolean isExpired = false;
}
