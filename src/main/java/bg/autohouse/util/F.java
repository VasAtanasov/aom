package bg.autohouse.util;

import bg.autohouse.data.models.Identifiable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Sets;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Collections.addAll;
import static java.util.Comparator.*;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.*;

/** F stands for utilities for functional programming style (mainly operating with collections). */
public final class F {

  @SafeVarargs
  public static <T> T firstNonNull(T... objects) {
    if (objects != null) {
      for (final T obj : objects) {
        if (obj != null) {
          return obj;
        }
      }
    }
    return null;
  }

  public static boolean anyNull(Object... objects) {
    return objects == null || Arrays.stream(objects).anyMatch(Objects::isNull);
  }

  public static boolean anyNonNull(Object... objects) {
    return firstNonNull(objects) != null;
  }

  public static boolean allNull(Object... objects) {
    return objects == null
        || objects.length > 0 && Arrays.stream(objects).allMatch(Objects::isNull);
  }

  public static boolean allNotNull(Object... objects) {
    return !isNullOrEmpty(objects) && Arrays.stream(objects).allMatch(Objects::nonNull);
  }

  public static <T> ArrayList<T> newListWithCapacityOf(Iterable<?> iterable) {
    final OptionalInt sizeOpt = sizeMaybe(iterable);
    return sizeOpt.isPresent() ? new ArrayList<>(sizeOpt.getAsInt()) : new ArrayList<>();
  }

  @SuppressWarnings("SortedCollectionWithNonComparableKeys")
  @SafeVarargs
  public static <T> TreeSet<T> newSortedSet(T... values) {
    return values == null
            ? new TreeSet<>()
            : Arrays.stream(values).collect(toCollection(TreeSet::new));
  }

  public static <T> HashSet<T> newSetWithExpectedSizeOf(Iterable<?> iterable) {
    final OptionalInt sizeOpt = sizeMaybe(iterable);
    return sizeOpt.isPresent()
        ? Sets.newHashSetWithExpectedSize(sizeOpt.getAsInt())
        : new HashSet<>();
  }

  @SafeVarargs
  public static <E> LinkedHashSet<E> newLinkedHashSet(E... elements) {
    if (elements == null) {
      return Sets.newLinkedHashSetWithExpectedSize(0);
    }

    final LinkedHashSet<E> result = Sets.newLinkedHashSetWithExpectedSize(elements.length);
    addAll(result, elements);
    return result;
  }

  public static <K, V> HashMap<K, V> newMapWithCapacityOf(Iterable<?> iterable) {
    final OptionalInt sizeOpt = sizeMaybe(iterable);
    return sizeOpt.isPresent()
        ? Maps.newHashMapWithExpectedSize(sizeOpt.getAsInt())
        : new HashMap<>();
  }

  public static String[] concat(final String[] arr1, final String... arr2) {
    return ObjectArrays.concat(arr1, arr2, String.class);
  }

  @SafeVarargs
  public static <T> List<T> concat(List<? extends T>... lists) {
    requireNonNull(lists);
    return Arrays.stream(lists).flatMap(List::stream).collect(toList());
  }

  public static <T> List<T> filterToList(
      Iterable<? extends T> iterable, Predicate<? super T> predicate) {
    requireNonNull(iterable, "iterable is null");
    requireNonNull(predicate, "predicate is null");
    return stream(iterable).filter(predicate).collect(toList());
  }

  public static <E extends Enum<E>> EnumSet<E> filterToEnumSet(
      Class<E> enumType, Predicate<? super E> predicate) {
    requireNonNull(enumType, "enumType is null");
    requireNonNull(predicate, "predicate is null");
    final EnumSet<E> result = EnumSet.noneOf(enumType);
    for (final E enumValue : enumType.getEnumConstants()) {
      if (predicate.test(enumValue)) {
        result.add(enumValue);
      }
    }

    return result;
  }

  public static <T> Set<T> filterToSet(
      Iterable<? extends T> iterable, Predicate<? super T> predicate) {
    requireNonNull(iterable, "iterable is null");
    requireNonNull(predicate, "predicate is null");
    return stream(iterable).filter(predicate).collect(toSet());
  }

  public static <S, T, C extends Collection<T>> C mapNonNulls(
      Iterable<? extends S> iterable, C destination, Function<? super S, ? extends T> mapper) {
    requireNonNull(iterable, "iterable is null");
    requireNonNull(destination, "destination is null");
    requireNonNull(mapper, "mapper is null");
    return mapNonNullsInternal(stream(iterable), mapper, () -> destination);
  }

  public static <S, T> ArrayList<T> mapNonNullsToList(
      Iterable<? extends S> iterable, Function<? super S, T> mapper) {
    requireNonNull(iterable, "iterable is null");
    requireNonNull(mapper, "mapper is null");
    return mapNonNullsInternal(stream(iterable), mapper, () -> newListWithCapacityOf(iterable));
  }

  public static <S, T> HashSet<T> mapNonNullsToSet(
      Iterable<? extends S> iterable, Function<? super S, T> mapper) {
    requireNonNull(iterable, "iterable is null");
    requireNonNull(mapper, "mapper is null");
    return mapNonNullsInternal(stream(iterable), mapper, () -> newSetWithExpectedSizeOf(iterable));
  }

  public static <S, T> ArrayList<T> mapNonNullsToList(
      S[] array, Function<? super S, ? extends T> mapper) {
    requireNonNull(array, "array is null");
    return mapNonNullsInternal(Arrays.stream(array), mapper, () -> new ArrayList<>(array.length));
  }

  public static <S, T> HashSet<T> mapNonNullsToSet(
      S[] array, Function<? super S, ? extends T> mapper) {
    requireNonNull(array, "array is null");
    return mapNonNullsInternal(
        Arrays.stream(array), mapper, () -> Sets.newHashSetWithExpectedSize(array.length));
  }

  private static <S, T, C extends Collection<T>> C mapNonNullsInternal(
      final Stream<? extends S> sourceStream,
      final Function<? super S, ? extends T> mapper,
      final Supplier<C> collectionSupplier) {
    return sourceStream
        .map(mapper)
        .filter(Objects::nonNull)
        .collect(toCollection(collectionSupplier));
  }

  public static <T> Map<Boolean, List<T>> partition(
      Iterable<? extends T> iterable, Predicate<? super T> predicate) {
    requireNonNull(iterable, "iterable is null");
    requireNonNull(predicate, "predicate is null");
    return stream(iterable).collect(partitioningBy(predicate));
  }

  public static boolean isNullOrEmpty(Iterable<?> iterable) {
    return iterable == null || Iterables.isEmpty(iterable);
  }

  @SafeVarargs
  public static <T> boolean isNullOrEmpty(T... objects) {
    return objects == null || objects.length == 0;
  }

  @SafeVarargs
  public static <T extends Comparable<? super T>> T getFirstByNaturalOrderNullsFirst(
      final T... values) {
    return getFirst(naturalOrder(), true, values);
  }

  @SafeVarargs
  public static <T extends Comparable<? super T>> T getFirstByNaturalOrderNullsLast(
      final T... values) {
    return getFirst(naturalOrder(), false, values);
  }

  @SafeVarargs
  public static <T extends Comparable<? super T>> T getFirst(
      Comparator<T> comparator, final boolean nullsFirst, T... values) {
    requireNonNull(comparator, "comparator is null");
    return values == null || values.length == 0
        ? null
        : Arrays.stream(values)
            .sorted(nullsFirst ? nullsFirst(comparator) : nullsLast(comparator))
            .limit(1L)
            .collect(toCollection(() -> new ArrayList<>(1)))
            .get(0);
  }

  public static <K, V> Map.Entry<K, V> entry(K key, V value) {
    return new AbstractMap.SimpleEntry<>(key, value);
  }

  @SafeVarargs
  public static <T> boolean containsAny(Iterable<? extends T> iterable, T... values) {
    requireNonNull(iterable, "iterable is null");
    if (values != null) {
      for (final T value : values) {
        if (Iterables.contains(iterable, value)) {
          return true;
        }
      }
    }
    return false;
  }

  public static <T> boolean containsAny(Iterable<T> iterable, Iterable<T> values) {
    requireNonNull(iterable, "iterable is null");
    requireNonNull(values, "values is null");
    for (final T value : values) {
      if (Iterables.contains(iterable, value)) {
        return true;
      }
    }
    return false;
  }

  @SafeVarargs
  public static <T> boolean containsAll(Iterable<? extends T> iterable, T... values) {
    requireNonNull(iterable, "iterable is null");
    if (values != null) {
      for (final T value : values) {
        if (!Iterables.contains(iterable, value)) {
          return false;
        }
      }
    }
    return true;
  }

  public static <T> boolean containsAll(Iterable<T> iterable, Iterable<T> values) {
    requireNonNull(iterable, "iterable is null");
    requireNonNull(values, "values is null");
    for (final T value : values) {
      if (!Iterables.contains(iterable, value)) {
        return false;
      }
    }
    return true;
  }

  private static OptionalInt sizeMaybe(Iterable<?> iterable) {
    requireNonNull(iterable);

    return Collection.class.isAssignableFrom(iterable.getClass())
        ? OptionalInt.of(Collection.class.cast(iterable).size())
        : Iterables.isEmpty(iterable) ? OptionalInt.of(0) : OptionalInt.empty();
  }

  public static <T> Stream<T> stream(Iterable<T> iterable) {
    return StreamSupport.stream(requireNonNull(iterable).spliterator(), false);
  }

  public static <T, S> Stream<T> mapToStream(Iterable<S> iterable, Function<? super S, T> mapper) {
    return StreamSupport.stream(requireNonNull(iterable).spliterator(), false).map(mapper);
  }

  public static <T> Stream<T> filterToStream(Iterable<T> iterable, Predicate<? super T> predicate) {
    return stream(iterable).filter(predicate);
  }

  public static <T> Stream<T> stream(T object, Iterable<? extends T> iterable) {
    final Stream<T> first = object != null ? Stream.of(object) : Stream.empty();
    return Stream.concat(first, stream(iterable));
  }

  public static <T, U extends Comparable<? super U>> U max(
      Iterable<? extends T> iterable, Function<? super T, ? extends U> extractor) {
    requireNonNull(iterable, "iterable is null");
    requireNonNull(extractor, "extractor is null");
    return stream(iterable)
        .filter(Objects::nonNull)
        .map(extractor)
        .filter(Objects::nonNull)
        .max(naturalOrder())
        .orElse(null);
  }

  public static <T, U extends Comparable<? super U>> U nullSafeMax(
      final T first, T second, Function<? super T, ? extends U> mapper) {
    requireNonNull(mapper, "mapper is null");
    final Optional<U> secondValueOpt = Optional.ofNullable(second).map(mapper);
    return Optional.ofNullable(first)
        .map(mapper)
        .map(
            firstValue -> {
              return secondValueOpt
                  .filter(secondValue -> firstValue.compareTo(secondValue) <= 0)
                  .orElse(firstValue);
            })
        .orElseGet(() -> secondValueOpt.orElse(null));
  }

  public static int coalesceAsInt(Number n, final int defaultValue) {
    return n != null ? n.intValue() : defaultValue;
  }

  public static <T, U> Map<U, Long> countByApplication(
      Stream<? extends T> stream, Function<? super T, U> classifier) {
    requireNonNull(stream, "stream is null");
    requireNonNull(classifier, "classifier is null");
    return stream.collect(groupingBy(classifier, counting()));
  }

  public static <T> void consumeIfNonEmpty(
      Collection<T> collection, Consumer<Collection<T>> consumer) {
    requireNonNull(collection, "collection is null");
    requireNonNull(consumer, "consumer is null");
    if (!collection.isEmpty()) {
      consumer.accept(collection);
    }
  }

  public static <ID> ID getId(Identifiable<ID> identifiable) {
    return identifiable == null ? null : identifiable.getId();
  }

  private F() {
    throw new AssertionError();
  }
}
