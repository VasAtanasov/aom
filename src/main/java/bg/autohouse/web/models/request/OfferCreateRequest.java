package bg.autohouse.web.models.request;

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
  @NotNull @Valid private VehicleCreateRequest vehicle;
  @NotNull @Positive private Integer price;
  @NotBlank private String description;
  @Builder.Default private List<MultipartFile> images = new ArrayList<>();
}
