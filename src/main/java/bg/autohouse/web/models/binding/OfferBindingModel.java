package bg.autohouse.web.models.binding;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

import static bg.autohouse.common.Constants.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferBindingModel {

    @Valid
    private VehicleBindingModel vehicle;

    @Positive
    @NotNull(message = EXCEPTION_LOCATION_NOT_EMPTY_NOT_NULL)
    private Long location;

    @Positive
    @NotNull(message = EXCEPTION_PRICE_NOT_NUlL)
    private Integer price;

    private String description;

    private List<MultipartFile> files;
}
