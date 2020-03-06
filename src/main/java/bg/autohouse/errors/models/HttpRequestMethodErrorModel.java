package bg.autohouse.errors.models;

import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import org.springframework.http.HttpMethod;

@Getter
@Setter
@Builder
public class HttpRequestMethodErrorModel implements Serializable {
  private static final long serialVersionUID = 4115067500106084449L;

  private final String actualMethod;

  @Singular private final List<HttpMethod> supportedMethods;
}
