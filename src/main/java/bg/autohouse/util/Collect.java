package bg.autohouse.util;

import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.minBy;

import bg.autohouse.data.models.Identifiable;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class Collect {

  public static <T, K, U, M extends Map<K, U>> Collector<T, ?, M> toMap(
      Function<? super T, ? extends K> keyMapper,
      Function<? super T, ? extends U> valueMapper,
      Supplier<M> mapSupplier) {

    return Collectors.toMap(keyMapper, valueMapper, throwingMerger(), mapSupplier);
  }

  public static <K, U> Collector<Map.Entry<K, U>, ?, Map<K, U>> entriesToMap() {
    return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
  }

  public static <T, K extends Comparable<? super K>, U>
      Collector<T, ?, ImmutableSortedMap<K, U>> toImmutableSortedMap(
          Function<? super T, ? extends K> keyMapper,
          Function<? super T, ? extends U> valueMapper) {

    return ImmutableSortedMap.toImmutableSortedMap(
        Comparator.<K>naturalOrder(), keyMapper, valueMapper);
  }

  public static <T, K> Collector<T, ?, Map<K, T>> indexingBy(Function<? super T, K> keyMapper) {
    requireNonNull(keyMapper);
    return Collectors.toMap(keyMapper, identity());
  }

  public static <T, K extends Identifiable<ID>, ID> Collector<T, ?, Map<ID, T>> indexingByIdOf(
      Function<? super T, K> keyMapper) {
    requireNonNull(keyMapper);
    return indexingBy(keyMapper.andThen(Identifiable::getId));
  }

  public static <T, K extends Identifiable<ID>, ID>
      Collector<T, ?, Map<ID, List<T>>> groupingByIdOf(Function<? super T, K> classifier) {
    requireNonNull(classifier);
    return groupingBy(classifier.andThen(Identifiable::getId));
  }

  public static <T, K> Collector<T, ?, Map<K, List<T>>> nullSafeGroupingBy(
      Function<? super T, K> classifier) {
    requireNonNull(classifier);

    final BiConsumer<Map<K, List<T>>, T> accumulator =
        (map, element) -> {
          requireNonNull(element);

          final K key = classifier.apply(element);

          if (key != null) {
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(element);
          }
        };

    return Collector.of(HashMap::new, accumulator, mapCombiner());
  }

  public static <T, K extends Identifiable<ID>, ID>
      Collector<T, ?, Map<ID, List<T>>> nullSafeGroupingByIdOf(Function<? super T, K> classifier) {
    requireNonNull(classifier);

    return nullSafeGroupingBy(
        t -> {
          final K key = classifier.apply(t);
          return key != null ? key.getId() : null;
        });
  }

  public static <K, V> Collector<V, ?, Map<K, V>> leastAfterGroupingBy(
      Function<? super V, ? extends K> classifier, Comparator<? super V> comparator) {

    requireNonNull(classifier, "classifier is null");
    requireNonNull(comparator, "comparator is null");

    return groupingBy(classifier, collectingAndThen(minBy(comparator), o -> o.orElse(null)));
  }

  public static <K, V> Collector<V, ?, Map<K, V>> greatestAfterGroupingBy(
      Function<? super V, ? extends K> classifier, Comparator<? super V> comparator) {

    requireNonNull(classifier, "classifier is null");
    requireNonNull(comparator, "comparator is null");

    return groupingBy(classifier, collectingAndThen(maxBy(comparator), o -> o.orElse(null)));
  }

  public static <T, V> Collector<T, ?, Map<T, V>> mappingTo(
      Function<? super T, ? extends V> valueMapper) {
    requireNonNull(valueMapper);
    return Collectors.toMap(identity(), valueMapper);
  }

  public static <T, U> Collector<T, ?, U> mappingAndCollectingFirst(
      Function<? super T, ? extends U> mapper) {
    requireNonNull(mapper);
    return mapping(
        mapper, collectingAndThen(Collectors.toList(), list -> Iterables.getFirst(list, null)));
  }

  public static <ID, T extends Identifiable<ID>> Collector<T, ?, List<ID>> idList() {
    return toListBy(nonNullIdAccumulator());
  }

  public static <ID, T extends Identifiable<ID>> Collector<T, ?, Set<ID>> idSet() {
    return toSetBy(nonNullIdAccumulator());
  }

  public static <T, U> Collector<T, List<U>, List<U>> toListBy(BiConsumer<List<U>, T> accumulator) {
    requireNonNull(accumulator);
    return Collector.of(ArrayList::new, accumulator, collectionCombiner());
  }

  public static <T, U> Collector<T, Set<U>, Set<U>> toSetBy(BiConsumer<Set<U>, T> accumulator) {
    requireNonNull(accumulator);
    return Collector.of(HashSet::new, accumulator, collectionCombiner());
  }

  private static <T, C extends Collection<T>> BinaryOperator<C> collectionCombiner() {
    return (left, right) -> {
      left.addAll(right);
      return left;
    };
  }

  private static <K, V, M extends Map<K, V>> BinaryOperator<M> mapCombiner() {
    return (left, right) -> {
      left.putAll(right);
      return left;
    };
  }

  private static <ID, C extends Collection<ID>, T extends Identifiable<ID>>
      BiConsumer<C, T> nonNullIdAccumulator() {
    return (collection, identifiable) -> {
      final ID id = F.getId(identifiable);

      if (id != null) {
        collection.add(id);
      }
    };
  }

  private static <T> BinaryOperator<T> throwingMerger() {
    return (u, v) -> {
      throw new IllegalStateException(String.format("Duplicate key %s", u));
    };
  }

  private Collect() {
    throw new AssertionError();
  }
}
