package bg.autohouse.util;

import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EnumUtils {

  public static <T extends Enum<T>, Textable> LinkedHashMap<Object, String> ENUM_MAP(
      Class<? extends Textable> enumClass) {

    LinkedHashMap<Object, String> map = new LinkedHashMap<>();
    Stream.of(enumClass.getEnumConstants())
        .sorted((a, b) -> a.toString().compareToIgnoreCase(b.toString()))
        .forEach(t -> map.put(t, t.toString()));

    return map;
  }

  // public static <T extends Enum<T>> LinkedHashMap<T, String> ENUM_MAP(Class<T> enumClass) {
  //   LinkedHashMap<T, String> map = new LinkedHashMap<>();
  //   Stream.of(enumClass.getEnumConstants())
  //       .sorted((a, b) -> a.toString().compareToIgnoreCase(b.toString()))
  //       .forEach(t -> map.put(t, t.toString()));
  //   return map;
  // }

  public static <T extends Enum<T>> Optional<T> fromString(String string, Class<T> enumClass) {
    return Stream.of(enumClass.getEnumConstants())
        .filter(t -> t.name().equalsIgnoreCase(string))
        .findAny();
  }
}