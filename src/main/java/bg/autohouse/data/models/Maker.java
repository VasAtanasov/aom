package bg.autohouse.data.models;

import javax.persistence.*;
import javax.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = false)
@EqualsAndHashCode(of = "name", callSuper = true)
@Entity
@Table(
    name = EntityConstants.MAKERS,
    uniqueConstraints = {
      @UniqueConstraint(
          name = "uk_makers_name",
          columnNames = {"name"})
    })
public class Maker extends BaseLongEntity {

  private static final long serialVersionUID = -2811586455073721689L;

  @Column(name = "name", nullable = false)
  private String name;
}
