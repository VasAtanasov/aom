package bg.autohouse.service.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DealershipServiceModel {
  private String name;
  private String description;
  private Long locationId;
  private String address;
}
