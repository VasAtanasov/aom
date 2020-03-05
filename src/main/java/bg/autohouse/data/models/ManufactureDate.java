package bg.autohouse.data.models;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ManufactureDate {
  @Column(name = "month", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT(0)")
  private Integer month;

  @Column(name = "year", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT(0)")
  private Integer year;
}
