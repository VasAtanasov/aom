package bg.autohouse.util;

import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EnumUtils {

  public static <T extends Enum<T>, Textable> LinkedHashMap<Object, String> ENUM_MAP_OF_TEXTABLE(
      Class<? extends Textable> enumClass) {

    LinkedHashMap<Object, String> map = new LinkedHashMap<>();
    Stream.of(enumClass.getEnumConstants())
        .sorted((a, b) -> a.toString().compareToIgnoreCase(b.toString()))
        .forEach(t -> map.put(t, t.toString()));

    return map;
  }

  public static <T extends Enum<T>> LinkedHashMap<T, String> ENUM_MAP(Class<T> enumClass) {
    LinkedHashMap<T, String> map = new LinkedHashMap<>();
    Stream.of(enumClass.getEnumConstants())
        .sorted((a, b) -> a.toString().compareToIgnoreCase(b.toString()))
        .forEach(t -> map.put(t, t.toString()));
    return map;
  }

  public static <T extends Enum<T>> Optional<T> fromString(String string, Class<T> enumClass) {
    return Stream.of(enumClass.getEnumConstants())
        .filter(t -> t.name().equalsIgnoreCase(string))
        .findAny();
  }

  /**
   * Maps value to other similar valued enumeration. Meant to be used with mapping enum values
   * from/to generated classes
   *
   * @param clazz Class to convert the value to
   * @param value Value to be casted
   * @return Converted value. Null if null was passed in.
   */
  public static <A extends Enum<A>, B extends Enum<B>> B convertNullableByEnumName(
      final Class<B> clazz, final A value) {
    return value == null ? null : Enum.valueOf(clazz, value.name());
  }
}
