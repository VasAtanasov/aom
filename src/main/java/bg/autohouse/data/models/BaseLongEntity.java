package bg.autohouse.data.models;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@MappedSuperclass
public abstract class BaseLongEntity extends BaseEntity<Long> implements Persistable<Long> {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, unique = true, nullable = false)
  @Access(AccessType.PROPERTY)
  private Long id;

  @Override
  @Transient
  public boolean isNew() {
    return null == getId();
  }
}
