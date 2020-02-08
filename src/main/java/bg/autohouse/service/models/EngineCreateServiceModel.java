package bg.autohouse.service.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EngineCreateServiceModel {

    private String modification;
    private String  fuelType;
    private Integer cylinders;
    private Integer power;
    private String powerType;
    private String  euroStandard;
}
