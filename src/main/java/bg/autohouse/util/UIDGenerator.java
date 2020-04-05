package bg.autohouse.util;

import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UIDGenerator {

  public static String generateId() {
    return UUID.randomUUID().toString();
  }
}
