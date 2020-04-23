package bg.autohouse.util;

import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UIDUtil {

  public static String generateStringUid() {
    return UUID.randomUUID().toString();
  }

  public static UUID generateUID() {
    return UUID.randomUUID();
  }

  public UUID convert(String source) {
    return (Assert.has(source) ? UUID.fromString(source.trim()) : null);
  }
}
