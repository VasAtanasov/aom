package bg.autohouse.data.models.account;

import bg.autohouse.data.models.ActionLog;
import bg.autohouse.data.models.BaseUuidEntity;
import bg.autohouse.data.models.EntityConstants;
import bg.autohouse.data.models.User;
import bg.autohouse.data.models.enums.AccountLogType;
import bg.autohouse.data.models.enums.ActionLogType;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = EntityConstants.ACCOUNT_LOGS)
public class AccountLog extends BaseUuidEntity implements ActionLog {

  private static final long serialVersionUID = -3591186610368572849L;

  @Enumerated(EnumType.STRING)
  @Column(name = "account_log_type", nullable = false, length = 50)
  private AccountLogType accountLogType;

  @ManyToOne private Account account;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @Column(name = "description", length = 255)
  private String description;

  @Override
  public ActionLogType getActionLogType() {
    return ActionLogType.ACCOUNT_LOG;
  }
}
