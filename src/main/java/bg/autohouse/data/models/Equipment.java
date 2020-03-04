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
@Table(name = "equipments")
public class Equipment extends BaseLongEntity {

  private static final long serialVersionUID = -5293962302684079808L;

  @Column(name = "name", unique = true, nullable = false)
  private String name;
}
