package bg.autohouse.utils;

import static org.assertj.core.api.Assertions.assertThat;

import bg.autohouse.util.Assert;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

public class AssertTest {

  @Test
  void when_isEmpty_withNull_shouldReturnTrue() {
    boolean isEmpty = Assert.isEmpty("");
    assertThat(isEmpty).isTrue();
  }

  @Test
  void when_isEmpty_withEmptyArray_shouldReturnTrue() {
    Object[] empty = {};
    assertThat(Assert.isEmpty(empty)).isTrue();
  }

  @Test
  void when_contains_ValidInput_shouldReturnTrue() {
    boolean contains = Assert.contains("contains text", "text");
    assertThat(contains).isTrue();
  }

  @Test
  void when_has_emptyArray_shouldReturnFalse() {
    Object[] empty = {};
    assertThat(Assert.has(empty)).isFalse();
    empty = null;
    assertThat(Assert.has(empty)).isFalse();
    final Object nullObject = null;
    Object[] withEmpty = {nullObject};
    assertThat(Assert.has(withEmpty)).isFalse();
  }

  @Test
  void when_isEmpty_withEmptyCollection_shouldReturnTrue() {
    List<Integer> emptyList = Collections.emptyList();
    assertThat(Assert.isEmpty(emptyList)).isTrue();
  }

  @Test
  void when_has_withNonEmptyCollection_shouldReturnTrue() {
    List<Integer> list = new ArrayList<>();
    list.add(Integer.valueOf(0));
    assertThat(Assert.has(list)).isTrue();
  }
}
