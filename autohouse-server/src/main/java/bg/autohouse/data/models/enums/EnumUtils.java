package bg.autohouse.data.models.enums;

import java.util.LinkedHashMap;
import java.util.stream.Stream;

public class EnumUtils {

    public static <T extends Enum<T> & Valueable<String>> LinkedHashMap<String, T> ENUM_MAP(Class<T> enumCass) {
        LinkedHashMap<String, T> map = new LinkedHashMap<>();
        Stream.of(enumCass.getEnumConstants())
                .sorted((a, b) -> a.getValue().compareToIgnoreCase(b.getValue()))
                .forEach(t -> map.put(t.getValue(), t));
        return map;
    }

    public static <T extends Enum<T>> T fromString(String string, Class<T> enumClass) {
        return Stream.of(enumClass.getEnumConstants())
                .filter(t -> t.name().equalsIgnoreCase(string))
                .findAny()
                .orElse(null);
    }

}
