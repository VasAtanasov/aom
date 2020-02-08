package bg.autohouse.web.models.binding;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static bg.autohouse.common.Constants.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EngineBindingModel {

    private String modification;

    @NotNull(message = EXCEPTION_FUEL_TYPE_NOT_EMPTY_NOT_NULL)
    @NotEmpty(message = EXCEPTION_FUEL_TYPE_NOT_EMPTY_NOT_NULL)
    private String fuelType;

    private Integer cylinders;

    @Positive
    @NotNull(message = EXCEPTION_POWER_NOT_EMPTY_NOT_NULL)
    private Integer power;

    @NotNull(message = EXCEPTION_POWER_TYPE_NOT_EMPTY_NOT_NULL)
    private String powerType;

    private String euroStandard;

}
