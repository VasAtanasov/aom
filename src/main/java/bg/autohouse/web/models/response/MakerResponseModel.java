package bg.autohouse.web.models.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Class representing a maker basic information")
public class MakerResponseModel {

  @JsonIgnore private static final String ID_MESSAGE = "Primary identifier.";

  @JsonIgnore private static final String ONLY_FOR_GET_MESSAGE = "Used only for get operations";

  @ApiModelProperty(value = ID_MESSAGE)
  private Long id;

  @ApiModelProperty(value = ONLY_FOR_GET_MESSAGE, position = 2)
  private String name;

  @Builder
  public MakerResponseModel(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
