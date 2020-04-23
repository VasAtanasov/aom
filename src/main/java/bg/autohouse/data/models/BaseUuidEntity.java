package bg.autohouse.data.models;

import java.util.UUID;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseUuidEntity extends BaseEntity<UUID> implements Persistable<UUID> {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(unique = true, nullable = false, updatable = false, columnDefinition = "BINARY(16)")
  // @Column(unique = true, nullable = false, updatable = false)
  @Access(AccessType.PROPERTY)
  private UUID id;

  @Override
  @Transient
  public boolean isNew() {
    return null == getId();
  }
}
