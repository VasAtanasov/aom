package bg.autohouse.util;

import java.util.LinkedHashMap;
import java.util.Map;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Payload {

  public static PayloadBuilder builder() {
    return new PayloadBuilder();
  }

  public static class PayloadBuilder {
    private Map<String, Object> object = new LinkedHashMap<>();

    public PayloadBuilder field(String key, Object value) {
      this.object.putIfAbsent(key, value);
      return (this);
    }

    public Map<String, Object> build() {
      return object;
    }
  }
}
