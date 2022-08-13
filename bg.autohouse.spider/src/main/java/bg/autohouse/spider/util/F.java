package bg.autohouse.spider.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class F {
  public static <K, V, T> Map<K, V> toHashMap(
      List<T> collection,
      Function<? super T, ? extends K> keyMapper,
      Function<? super T, ? extends V> valueMapper) {
    return collection.stream()
        .collect(Collectors.toMap(keyMapper, valueMapper, (a, b) -> a, HashMap::new));
  }
}
