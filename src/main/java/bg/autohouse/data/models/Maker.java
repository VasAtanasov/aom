package bg.autohouse.data.models;

import javax.persistence.*;
import javax.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
    name = "makers",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "uk_makers_name",
          columnNames = {"name"})
    })
public class Maker extends BaseLongEntity {

  private static final long serialVersionUID = -2811586455073721689L;

  @Column(name = "name", unique = true, nullable = false)
  private String name;

  @Builder
  public Maker(final String name) {
    super(null);
    this.name = name;
  }
}
