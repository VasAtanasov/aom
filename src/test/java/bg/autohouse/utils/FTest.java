package bg.autohouse.utils;

import static java.util.Arrays.asList;
import static java.util.Arrays.sort;
import static java.util.Collections.emptyList;
import static org.junit.Assert.*;

import bg.autohouse.util.F;
import com.google.common.collect.ImmutableMap;

import java.util.*;
import java.util.stream.Stream;

import org.junit.Test;

public class FTest {

  private enum TestEnum {
    A,
    B,
    C;
  }

  private static class TestEnumHolder {
    private final TestEnum value;

    TestEnumHolder(final TestEnum value) {
      this.value = value;
    }

    public TestEnum getValue() {
      return value;
    }
  }

  @Test
  public void testFirstNonNull() {
    final Integer obj = 1;

    assertNull(F.firstNonNull());
    assertNull(null);
    assertNull(F.firstNonNull(null, null));
    assertEquals(obj, F.firstNonNull(obj));
    assertEquals(obj, F.firstNonNull(obj, null));
    assertEquals(obj, F.firstNonNull(null, obj));
    assertEquals(obj, F.firstNonNull(null, obj, null));
  }

  @Test
  public void testAnyNull() {
    final Integer obj = 1;

    assertFalse(F.anyNull());
    assertTrue(F.anyNull((Object[]) null));
    assertTrue(F.anyNull(null, null));
    assertTrue(F.anyNull(null, obj));
    assertTrue(F.anyNull(obj, null));
    assertFalse(F.anyNull(obj, 2));
  }

  @Test
  public void testAnyNonNull() {
    final Integer obj = 1;

    assertFalse(F.anyNonNull());
    assertFalse(F.anyNonNull((Object[]) null));
    assertFalse(F.anyNonNull(null, null));
    assertTrue(F.anyNonNull(null, obj));
    assertTrue(F.anyNonNull(obj, null));
    assertTrue(F.anyNonNull(obj, 2));
  }

  @Test
  public void testAllNull() {
    final Integer obj = 1;

    assertFalse(F.allNull());
    assertTrue(F.allNull((Object[]) null));
    assertTrue(F.allNull(null, null));
    assertFalse(F.allNull(null, obj));
    assertFalse(F.allNull(obj, null));
    assertFalse(F.allNull(obj, 2));
  }

  @Test
  public void testAllNotNull() {
    final Integer obj = 1;

    assertFalse(F.allNotNull());
    assertFalse(F.allNotNull((Object[]) null));
    assertFalse(F.allNotNull(null, null));
    assertFalse(F.allNotNull(null, obj));
    assertFalse(F.allNotNull(obj, null));
    assertTrue(F.allNotNull(obj, 2));
  }

  @Test
  public void testFilterToEnumSet() {
    assertEquals(EnumSet.noneOf(TestEnum.class), F.filterToEnumSet(TestEnum.class, t -> false));
    assertEquals(EnumSet.allOf(TestEnum.class), F.filterToEnumSet(TestEnum.class, t -> true));
    assertEquals(EnumSet.of(TestEnum.C), F.filterToEnumSet(TestEnum.class, TestEnum.C::equals));
    assertEquals(
        EnumSet.of(TestEnum.A, TestEnum.B),
        F.filterToEnumSet(TestEnum.class, t -> t != TestEnum.C));
  }

  @Test
  public void testMax() {
    final TestEnumHolder holdsA = new TestEnumHolder(TestEnum.A);
    final TestEnumHolder holdsB = new TestEnumHolder(TestEnum.B);
    final TestEnumHolder holdsC = new TestEnumHolder(TestEnum.C);
    final TestEnumHolder holdsNull = new TestEnumHolder(null);

    assertEquals(TestEnum.C, F.max(asList(holdsA, holdsB, holdsC), TestEnumHolder::getValue));
    assertEquals(TestEnum.A, F.max(asList(holdsA, holdsNull, null), TestEnumHolder::getValue));
    assertNull(F.max(asList(holdsNull, null), TestEnumHolder::getValue));
    assertNull(F.max(emptyList(), TestEnumHolder::getValue));
  }

  @Test
  public void testNullSafeMax() {
    final TestEnumHolder holdsA = new TestEnumHolder(TestEnum.A);
    final TestEnumHolder holdsB = new TestEnumHolder(TestEnum.B);
    final TestEnumHolder holdsC = new TestEnumHolder(TestEnum.C);
    final TestEnumHolder holdsNull = new TestEnumHolder(null);

    assertEquals(TestEnum.B, F.nullSafeMax(holdsA, holdsB, TestEnumHolder::getValue));
    assertEquals(TestEnum.C, F.nullSafeMax(holdsC, holdsA, TestEnumHolder::getValue));

    assertEquals(TestEnum.A, F.nullSafeMax(holdsA, holdsNull, TestEnumHolder::getValue));
    assertEquals(TestEnum.B, F.nullSafeMax(holdsNull, holdsB, TestEnumHolder::getValue));

    assertEquals(TestEnum.B, F.nullSafeMax(holdsB, null, TestEnumHolder::getValue));
    assertEquals(TestEnum.C, F.nullSafeMax(null, holdsC, TestEnumHolder::getValue));

    assertNull(F.nullSafeMax(holdsNull, holdsNull, TestEnumHolder::getValue));
    assertNull(F.nullSafeMax(null, holdsNull, TestEnumHolder::getValue));
    assertNull(F.nullSafeMax(holdsNull, null, TestEnumHolder::getValue));
    assertNull(F.nullSafeMax(null, null, TestEnumHolder::getValue));
  }

  @Test
  public void testCountByApplication() {
    final List<String> names = asList("Bob", "Dylan", "James", "Jeff", "Joe", "Leroy");

    assertEquals(
        ImmutableMap.of(3, 2L, 4, 1L, 5, 3L), F.countByApplication(names.stream(), String::length));
  }

  @Test
  public void testNewSortedSet() {
    TreeSet<Integer> sortedSet = F.newSortedSet(3, 2, 1);
    assertEquals(0, sortedSet.iterator().next().compareTo(1));
    int a = 5;
  }

  @Test
  public void testNewLinkedHashSet() {
    final TestEnumHolder holdsA = new TestEnumHolder(TestEnum.A);
    final TestEnumHolder holdsB = new TestEnumHolder(TestEnum.B);
    final TestEnumHolder holdsC = new TestEnumHolder(TestEnum.C);
    LinkedHashSet<TestEnumHolder> set = F.newLinkedHashSet(holdsC, holdsB, holdsA);
    assertEquals(set.size(), 3);
    assertEquals(set.iterator().next(), holdsC);
  }

  @Test
  public void testNewLinkedHashSet_EmptySet() {
    LinkedHashSet<TestEnumHolder> set = F.newLinkedHashSet();
    assertEquals(set.size(), 0);
  }

  @Test
  public void testNewMapWithCapacityOf() {
    List<TestEnumHolder> list =
        List.of(new TestEnumHolder(TestEnum.A), new TestEnumHolder(TestEnum.B));
    HashMap<Object, Object> map = F.newMapWithCapacityOf(list);
    assertTrue(map.isEmpty());
  }

  @Test
  public void testConcatArrayOfStrings() {
    String[] array = {"string1", "string2"};
    String string3 = "string3";
    String[] newArray = F.concat(array, string3);
    assertEquals(newArray.length, 3);
  }

  @Test
  public void testConcatList() {
    final TestEnumHolder holdsA = new TestEnumHolder(TestEnum.A);
    final TestEnumHolder holdsB = new TestEnumHolder(TestEnum.B);
    final TestEnumHolder holdsC = new TestEnumHolder(TestEnum.C);
    List<TestEnumHolder> list1 = List.of(holdsA, holdsB);
    List<TestEnumHolder> list2 = List.of(holdsC);
    List<TestEnumHolder> newList = F.concat(list1, list2);
    assertEquals(newList.size(), 3);
  }

  @Test
  public void testFilterToSet() {
    List<Integer> list = List.of(12, 3, 4, 5, 5, 6, 7, 1, 2, 3);
    Set<Integer> set = F.filterToSet(list, n -> n <= 5);
    assertEquals(set.size(), 5);
  }

  @Test
  public void testMapNonNulls() {
    List<TestEnumHolder> list1 =
        Arrays.asList(
            new TestEnumHolder(TestEnum.A),
            new TestEnumHolder(TestEnum.B),
            new TestEnumHolder(TestEnum.C),
            new TestEnumHolder(null),
            new TestEnumHolder(null));
    List<TestEnum> nonNulls = F.mapNonNulls(list1, new ArrayList<>(), TestEnumHolder::getValue);
    assertEquals(nonNulls.size(), 3);
  }

  @Test
  public void testMapNonNullsToList() {
    List<TestEnumHolder> list1 =
        Arrays.asList(
            new TestEnumHolder(TestEnum.A),
            new TestEnumHolder(TestEnum.B),
            new TestEnumHolder(TestEnum.C),
            new TestEnumHolder(null),
            new TestEnumHolder(null));
    List<TestEnum> nonNulls = F.mapNonNullsToList(list1, TestEnumHolder::getValue);
    assertEquals(nonNulls.size(), 3);
  }

  @Test
  public void testMapNonNullsToSet() {
    List<TestEnumHolder> list1 =
        Arrays.asList(
            new TestEnumHolder(TestEnum.A),
            new TestEnumHolder(TestEnum.B),
            new TestEnumHolder(TestEnum.C),
            new TestEnumHolder(null),
            new TestEnumHolder(null));
    Set<TestEnum> nonNulls = F.mapNonNullsToSet(list1, TestEnumHolder::getValue);
    assertEquals(nonNulls.size(), 3);
  }

  @Test
  public void testPartition() {
    List<TestEnumHolder> list =
        Arrays.asList(
            new TestEnumHolder(TestEnum.A),
            new TestEnumHolder(TestEnum.B),
            new TestEnumHolder(TestEnum.B),
            new TestEnumHolder(TestEnum.B),
            new TestEnumHolder(TestEnum.C));
    Map<Boolean, List<TestEnumHolder>> map =
        F.partition(list, testEnumHolder -> testEnumHolder.getValue().equals(TestEnum.B));
    assertEquals(map.get(Boolean.TRUE).size(), 3);
  }

  @Test
  public void testGetFirstByNaturalOrderNullsFirst() {
    TestEnum[] arrayWithNulls = {TestEnum.A, TestEnum.B, null, TestEnum.C};
    TestEnum value = F.getFirstByNaturalOrderNullsFirst(arrayWithNulls);
    assertNull(value);
  }

  @Test
  public void testGetFirstByNaturalOrderNullsLast() {
    TestEnum[] arrayWithNulls = {TestEnum.B, null, TestEnum.C, TestEnum.A};
    TestEnum value = F.getFirstByNaturalOrderNullsLast(arrayWithNulls);
    assertNotNull(value);
    assertEquals(value, TestEnum.A);
  }

  @Test
  public void testContainsAnyOfValue() {
    List<TestEnum> enums = Arrays.asList(TestEnum.A, TestEnum.B);
    boolean isEnumA = F.containsAny(enums, TestEnum.A);
    assertTrue(isEnumA);
    boolean isEnumC = F.containsAny(enums, TestEnum.C);
    assertFalse(isEnumC);
  }

  @Test
  public void testContainsAnyOfValues() {
    List<TestEnum> enums = Arrays.asList(TestEnum.A, TestEnum.B);
    List<TestEnum> enumsWithA = Collections.singletonList(TestEnum.A);
    List<TestEnum> enumsWithC = Collections.singletonList(TestEnum.C);
    boolean isEnumA = F.containsAny(enums, enumsWithA);
    assertTrue(isEnumA);
    boolean isEnumC = F.containsAny(enums, enumsWithC);
    assertFalse(isEnumC);
  }

  @Test
  public void testContainsAllOfValues() {
    List<TestEnum> enums = Arrays.asList(TestEnum.A, TestEnum.B);
    List<TestEnum> enumsAll = Arrays.asList(TestEnum.A, TestEnum.B);
    List<TestEnum> enumsWithC = Arrays.asList(TestEnum.A, TestEnum.B, TestEnum.C);
    boolean isEnumA = F.containsAll(enums, enumsAll);
    assertTrue(isEnumA);
    boolean isEnumC = F.containsAll(enums, enumsWithC);
    assertFalse(isEnumC);
  }

  @Test
  public void testStreamConcat() {
    List<TestEnum> enums = Arrays.asList(TestEnum.A, TestEnum.B);
    Stream<TestEnum> stream = F.stream(TestEnum.C, enums);
    assertEquals(stream.count(), 3);
  }

  @Test
  public void testCoalesceAsInt() {
    int defaultValue = 5;
    int intValue = 1;
    double doubleValue = 1.6;
    int toIntValue = F.coalesceAsInt(intValue, defaultValue);
    int toIntDefaultValue1 = F.coalesceAsInt(doubleValue, defaultValue);
    int toIntDefaultValue2 = F.coalesceAsInt(null, defaultValue);
    assertEquals(toIntValue, intValue);
    assertEquals(toIntDefaultValue1, intValue);
    assertEquals(toIntDefaultValue2, defaultValue);
  }

  @Test
  public void testConsumeIfNonEmpty() {
    List<TestEnum> enums = Arrays.asList(TestEnum.A, TestEnum.B, TestEnum.C);
    List<TestEnum> enumsConsumed = new ArrayList<>();
    F.consumeIfNonEmpty(enums, enumsConsumed::addAll);
    assertEquals(enumsConsumed.size(), enums.size());
  }
}
