package bg.autohouse.errors.models;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import org.springframework.http.MediaType;

@Getter
@Setter
@Builder
@AllArgsConstructor(staticName = "of")
public class HttpMediaTypeErrorModel implements Serializable {
  private static final long serialVersionUID = 7301072886218818L;

  @Singular("mediaType")
  private List<MediaType> mediaType;
}
