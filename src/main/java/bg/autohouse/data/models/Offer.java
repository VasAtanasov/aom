package bg.autohouse.data.models;

import javax.persistence.*;
import lombok.*;

@Getter
@Setter
// @NoArgsConstructor
// @AllArgsConstructor
@Builder
@Entity
@Table(name = "offers")
public class Offer extends BaseUuidEntity {

  private static final long serialVersionUID = -2840866963962522737L;
}
