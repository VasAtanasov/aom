package bg.autohouse.data.models;

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
@Table(name = "locations")
public class Location extends BaseLongEntity {

  private static final long serialVersionUID = -2377398736911388136L;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Column(name = "maps_url")
  private String mapsUrl;
}
