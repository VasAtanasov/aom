package bg.autohouse.web.models.request.offer;

import bg.autohouse.validation.ValidationMessages;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Builder
public class OfferCreateRequest {
  @NotNull(message = ValidationMessages.MISSING_VEHICLE_DATA)
  @Valid
  private VehicleCreateRequest vehicle;

  @NotNull(message = ValidationMessages.NULL_VALUE_OFFER_PRICE)
  @Positive(message = ValidationMessages.INVALID_PRICE)
  private Integer price;

  @NotBlank(message = ValidationMessages.MISSING_INVALID_DESCRIPTION)
  private String description;

  @Builder.Default private List<MultipartFile> images = new ArrayList<>();
}
