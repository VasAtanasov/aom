package bg.autohouse.web.models.binding;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

import static bg.autohouse.common.Constants.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleBindingModel {

    @Valid
    private EngineBindingModel engine;

    @Positive
    @NotNull(message = EXCEPTION_MAKER_NOT_NUlL)
    private Long maker;

    @Positive
    @NotNull(message = EXCEPTION_MODEL_NOT_NUlL)
    private Long model;

    @NotNull(message = EXCEPTION_STATE_NOT_EMPTY_NOT_NULL)
    @NotEmpty(message = EXCEPTION_STATE_NOT_EMPTY_NOT_NULL)
    private String state;

    @Positive
    private Integer year;

    @Positive
    private Integer month;

    @NotNull(message = EXCEPTION_BODY_STYLE_NOT_EMPTY_NOT_NULL)
    @NotEmpty(message = EXCEPTION_BODY_STYLE_NOT_EMPTY_NOT_NULL)
    private String bodyStyle;

    private String transmission;

    private String drive;

    @Positive
    private Integer mileage;

    @Positive
    private Integer seats;

    @Positive
    private Integer doors;

    @Positive
    private Integer owners;

    @NotNull
    private Boolean hasAccident;

    @Positive
    private Long exteriorColor;

    @Positive
    private Long interiorColor;

    @Builder.Default
    private List<Long> features = new ArrayList<>();
}
