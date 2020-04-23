package bg.autohouse.data.models;

import bg.autohouse.data.models.enums.ActionLogType;
import bg.autohouse.data.models.enums.UserLogType;
import java.util.UUID;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = EntityConstants.USER_LOGS)
public class UserLog extends BaseUuidEntity implements ActionLog<UUID> {

  private static final long serialVersionUID = -7221643764722581633L;

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Enumerated(EnumType.STRING)
  @Column(name = "user_log_type", nullable = false, length = 50)
  private UserLogType userLogType;

  @Column(name = "description", length = 255)
  private String description;

  @Override
  public ActionLogType getActionLogType() {
    return ActionLogType.USER_LOG;
  }
}
