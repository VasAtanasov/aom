package bg.autohouse.data.models.account;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Embeddable
public class ContactDetails {
  @Column(name = "phone_number", nullable = true)
  private String phoneNumber;

  @Column(name = "web_link", nullable = true)
  private String webLink;
}
