package bg.autohouse.util;

import static java.util.Collections.addAll;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.nullsFirst;
import static java.util.Comparator.nullsLast;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Sets;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang.StringUtils;

/** F stands for utilities for functional programming style (mainly operating with collections). */
public final class F {

  /**
   * Lazily evaluate given suppliers as long as supplier returns Optional having value present.
   *
   * @param suppliers
   * @param <T>
   * @return
   */
  @SafeVarargs
  public static <T> Optional<T> optionalFromSuppliers(Supplier<Optional<T>>... suppliers) {
    return Arrays.stream(suppliers)
        .map(Supplier::get)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .findFirst();
  }

  @Nullable
  @SafeVarargs
  public static <T> T firstNonNull(@Nullable final T... objects) {
    if (objects != null) {
      for (final T obj : objects) {
        if (obj != null) {
          return obj;
        }
      }
    }
    return null;
  }

  @SafeVarargs
  public static boolean anyNull(@Nullable final Object... objects) {
    return objects == null || Arrays.stream(objects).anyMatch(Objects::isNull);
  }

  @SafeVarargs
  public static boolean anyNonNull(@Nullable final Object... objects) {
    return firstNonNull(objects) != null;
  }

  @SafeVarargs
  public static boolean allNull(@Nullable final Object... objects) {
    return objects == null
        || objects.length > 0 && Arrays.stream(objects).allMatch(Objects::isNull);
  }

  @SafeVarargs
  public static boolean allNotNull(@Nullable final Object... objects) {
    return !isNullOrEmpty(objects) && Arrays.stream(objects).allMatch(Objects::nonNull);
  }

  @Nonnull
  @SafeVarargs
  public static <T> TreeSet<T> newSortedSet(@Nullable final T... values) {
    return values == null
        ? new TreeSet<>()
        : Arrays.stream(values).collect(toCollection(TreeSet::new));
  }

  @Nonnull
  public static <T> ArrayList<T> newListWithCapacityOf(@Nonnull final Iterable<?> iterable) {
    final OptionalInt sizeOpt = sizeMaybe(iterable);
    return sizeOpt.isPresent() ? new ArrayList<>(sizeOpt.getAsInt()) : new ArrayList<>();
  }

  @Nonnull
  public static <T> HashSet<T> newSetWithExpectedSizeOf(@Nonnull final Iterable<?> iterable) {
    final OptionalInt sizeOpt = sizeMaybe(iterable);
    return sizeOpt.isPresent()
        ? Sets.newHashSetWithExpectedSize(sizeOpt.getAsInt())
        : new HashSet<>();
  }

  @SafeVarargs
  @Nonnull
  public static <E> LinkedHashSet<E> newLinkedHashSet(@Nullable final E... elements) {
    if (elements == null) {
      return Sets.newLinkedHashSetWithExpectedSize(0);
    }

    final LinkedHashSet<E> result = Sets.newLinkedHashSetWithExpectedSize(elements.length);
    addAll(result, elements);
    return result;
  }

  @Nonnull
  public static <K, V> HashMap<K, V> newMapWithCapacityOf(@Nonnull final Iterable<?> iterable) {
    final OptionalInt sizeOpt = sizeMaybe(iterable);
    return sizeOpt.isPresent()
        ? Maps.newHashMapWithExpectedSize(sizeOpt.getAsInt())
        : new HashMap<>();
  }

  @Nonnull
  public static String[] concat(final String[] arr1, final String... arr2) {
    return ObjectArrays.concat(arr1, arr2, String.class);
  }

  @Nonnull
  @SafeVarargs
  public static <T> List<T> concat(@Nonnull final List<? extends T>... lists) {
    requireNonNull(lists);
    return Arrays.stream(lists).flatMap(List::stream).collect(toList());
  }

  @Nonnull
  public static <T> List<T> filterToList(
      @Nonnull final Iterable<? extends T> iterable,
      @Nonnull final Predicate<? super T> predicate) {

    requireNonNull(iterable, "iterable is null");
    requireNonNull(predicate, "predicate is null");

    return stream(iterable).filter(predicate).collect(toList());
  }

  @Nonnull
  public static <E extends Enum<E>> EnumSet<E> filterToEnumSet(
      @Nonnull final Class<E> enumType, @Nonnull final Predicate<? super E> predicate) {

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

  @Nonnull
  public static <T> Set<T> filterToSet(
      @Nonnull final Iterable<? extends T> iterable,
      @Nonnull final Predicate<? super T> predicate) {

    requireNonNull(iterable, "iterable is null");
    requireNonNull(predicate, "predicate is null");

    return stream(iterable).filter(predicate).collect(toSet());
  }

  @Nonnull
  public static <S, T, C extends Collection<T>> C mapNonNulls(
      @Nonnull final Iterable<? extends S> iterable,
      @Nonnull final C destination,
      @Nonnull final Function<? super S, ? extends T> mapper) {

    requireNonNull(iterable, "iterable is null");
    requireNonNull(destination, "destination is null");
    requireNonNull(mapper, "mapper is null");

    return mapNonNullsInternal(stream(iterable), mapper, () -> destination);
  }

  @Nonnull
  public static <S, T> ArrayList<T> mapNonNullsToList(
      @Nonnull final Iterable<? extends S> iterable, @Nonnull final Function<? super S, T> mapper) {

    requireNonNull(iterable, "iterable is null");
    requireNonNull(mapper, "mapper is null");

    return mapNonNullsInternal(stream(iterable), mapper, () -> newListWithCapacityOf(iterable));
  }

  @Nonnull
  public static <S, T> HashSet<T> mapNonNullsToSet(
      @Nonnull final Iterable<? extends S> iterable, @Nonnull final Function<? super S, T> mapper) {

    requireNonNull(iterable, "iterable is null");
    requireNonNull(mapper, "mapper is null");

    return mapNonNullsInternal(stream(iterable), mapper, () -> newSetWithExpectedSizeOf(iterable));
  }

  @Nonnull
  public static <S, T> ArrayList<T> mapNonNullsToList(
      @Nonnull final S[] array, @Nonnull final Function<? super S, ? extends T> mapper) {

    requireNonNull(array, "array is null");

    return mapNonNullsInternal(Arrays.stream(array), mapper, () -> new ArrayList<>(array.length));
  }

  @Nonnull
  public static <S, T> HashSet<T> mapNonNullsToSet(
      @Nonnull final S[] array, @Nonnull final Function<? super S, ? extends T> mapper) {

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

  @Nonnull
  public static <T> Map<Boolean, List<T>> partition(
      @Nonnull final Iterable<? extends T> iterable,
      @Nonnull final Predicate<? super T> predicate) {

    requireNonNull(iterable, "iterable is null");
    requireNonNull(predicate, "predicate is null");

    return stream(iterable).collect(partitioningBy(predicate));
  }

  public static boolean isNullOrEmpty(@Nullable final Iterable<?> iterable) {
    return iterable == null || Iterables.isEmpty(iterable);
  }

  @SafeVarargs
  public static <T> boolean isNullOrEmpty(@Nullable final T... objects) {
    return objects == null || objects.length == 0;
  }

  @SafeVarargs
  @Nullable
  public static <T extends Comparable<? super T>> T getFirstByNaturalOrderNullsFirst(
      @Nullable final T... values) {
    return getFirst(naturalOrder(), true, values);
  }

  @SafeVarargs
  @Nullable
  public static <T extends Comparable<? super T>> T getFirstByNaturalOrderNullsLast(
      @Nullable final T... values) {
    return getFirst(naturalOrder(), false, values);
  }

  @SafeVarargs
  @Nullable
  public static <T extends Comparable<? super T>> T getFirst(
      @Nonnull final Comparator<T> comparator,
      final boolean nullsFirst,
      @Nullable final T... values) {

    requireNonNull(comparator, "comparator is null");

    return values == null || values.length == 0
        ? null
        : Arrays.stream(values)
            .sorted(nullsFirst ? nullsFirst(comparator) : nullsLast(comparator))
            .limit(1L)
            .collect(toCollection(() -> new ArrayList<>(1)))
            .get(0);
  }

  @Nonnull
  public static <K, V> Map.Entry<K, V> entry(K key, V value) {
    return new AbstractMap.SimpleEntry<>(key, value);
  }

  @SafeVarargs
  public static <T> boolean containsAny(
      @Nonnull final Iterable<? extends T> iterable, @Nullable final T... values) {
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

  public static <T> boolean containsAny(
      @Nonnull final Iterable<T> iterable, @Nonnull final Iterable<T> values) {
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
  public static <T> boolean containsAll(
      @Nonnull final Iterable<? extends T> iterable, @Nullable final T... values) {
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

  public static <T> boolean containsAll(
      @Nonnull final Iterable<T> iterable, @Nonnull final Iterable<T> values) {
    requireNonNull(iterable, "iterable is null");
    requireNonNull(values, "values is null");

    for (final T value : values) {
      if (!Iterables.contains(iterable, value)) {
        return false;
      }
    }
    return true;
  }

  private static OptionalInt sizeMaybe(@Nonnull final Iterable<?> iterable) {
    requireNonNull(iterable);

    return Collection.class.isAssignableFrom(iterable.getClass())
        ? OptionalInt.of(Collection.class.cast(iterable).size())
        : Iterables.isEmpty(iterable) ? OptionalInt.of(0) : OptionalInt.empty();
  }

  @Nonnull
  public static <T> Stream<T> stream(@Nonnull final Iterable<T> iterable) {
    return StreamSupport.stream(requireNonNull(iterable).spliterator(), false);
  }

  @Nonnull
  public static <T> Stream<T> stream(
      @Nullable final T object, @Nonnull final Iterable<? extends T> iterable) {
    final Stream<T> first = object != null ? Stream.of(object) : Stream.empty();
    return Stream.concat(first, stream(iterable));
  }

  @Nullable
  public static <T, U extends Comparable<? super U>> U max(
      @Nonnull final Iterable<? extends T> iterable,
      @Nonnull final Function<? super T, ? extends U> extractor) {

    requireNonNull(iterable, "iterable is null");
    requireNonNull(extractor, "extractor is null");

    return stream(iterable)
        .filter(Objects::nonNull)
        .map(extractor)
        .filter(Objects::nonNull)
        .max(naturalOrder())
        .orElse(null);
  }

  @Nullable
  public static <T, U extends Comparable<? super U>> U nullsafeMax(
      @Nullable final T first,
      @Nullable final T second,
      @Nonnull final Function<? super T, ? extends U> mapper) {

    requireNonNull(mapper, "mapper is null");

    final Optional<U> secondValueOpt = Optional.ofNullable(second).map(mapper);

    return Optional.ofNullable(first)
        .map(mapper)
        .map(
            firstValue -> {
              return secondValueOpt
                  .map(
                      secondValue ->
                          firstValue.compareTo(secondValue) > 0 ? firstValue : secondValue)
                  .orElse(firstValue);
            })
        .orElseGet(() -> secondValueOpt.orElse(null));
  }

  public static int coalesceAsInt(@Nullable final Number n, final int defaultValue) {
    return n != null ? n.intValue() : defaultValue;
  }

  @Nonnull
  public static <T, U> Map<U, Long> countByApplication(
      @Nonnull final Stream<? extends T> stream, @Nonnull final Function<? super T, U> classifier) {

    requireNonNull(stream, "stream is null");
    requireNonNull(classifier, "classifier is null");

    return stream.collect(groupingBy(classifier, counting()));
  }

  public static <T> void consumeIfNonEmpty(
      @Nonnull final Collection<T> collection, @Nonnull final Consumer<Collection<T>> consumer) {

    requireNonNull(collection, "collection is null");
    requireNonNull(consumer, "consumer is null");

    if (!collection.isEmpty()) {
      consumer.accept(collection);
    }
  }

  @Nonnull
  public static Optional<String> trimToOptional(@Nullable final String str) {
    return Optional.ofNullable(StringUtils.trimToNull(str));
  }

  @Nonnull
  public static <T> List<T> listFromOptional(@Nonnull Optional<T> optional) {
    return optional.map(ImmutableList::of).orElseGet(ImmutableList::of);
  }

  private F() {
    throw new AssertionError();
  }
}
