package bg.autohouse.service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailServiceModel {
  private String toAddress;
  private String toName;
  private String fromName;
  private String fromAddress;
  private String subject;
  private String textContent;
  private String htmlContent;
}
