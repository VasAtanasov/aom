package bg.autohouse.data.models.geo;

import bg.autohouse.data.models.BaseLongEntity;
import bg.autohouse.data.models.EntityConstants;
import javax.persistence.*;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = EntityConstants.LOCATIONS)
public class Location extends BaseLongEntity {

  private static final long serialVersionUID = -2377398736911388136L;

  @ManyToOne(targetEntity = Province.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "province_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = EntityConstants.PREFIX + "fk_locations_provinces_id"))
  private Province province;

  @Column(name = "city", nullable = false)
  private String city;

  @Column(name = "city_region", nullable = false)
  private String cityRegion;

  @Column(name = "country", nullable = false)
  private String country;

  @Column(name = "postal_code")
  private Integer postalCode;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "latitude", column = @Column(nullable = true)),
    @AttributeOverride(name = "longitude", column = @Column(nullable = true))
  })
  private GeoLocation geo;

  @Column(name = "maps_url")
  private String mapsUrl;
}
