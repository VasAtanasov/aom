package bg.autohouse.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Assert implements Serializable {

  /** */
  private static final long serialVersionUID = 1L;

  /** @return TRUE assertion when given objects is not null, FALSE otherwise */
  public static boolean has(Object object) {
    return object != null;
  }

  public static boolean isEmpty(Object object) {
    return !has(object);
  }

  /** @return TRUE when given text has any character, FALSE otherwise */
  public static boolean has(String text) {
    if (text != null && text.trim().length() > 0) {
      return true;
    }
    return false;
  }

  public static boolean isEmpty(String text) {
    return !has(text);
  }

  /** @return TRUE when given text contains the given substring, FALSE otherwise */
  public static boolean contains(String textToSearch, String substring) {
    if (textToSearch != null && textToSearch.contains(substring)) {
      return true;
    }
    return false;
  }

  /**
   * @return TRUE when given array has elements; that is, it must not be {@code null} and must have
   *     at least one element. FALSE otherwise
   */
  public static boolean has(Object[] array) {
    if (array == null || array.length == 0) {
      return false;
    }
    for (Object element : array) {
      if (element != null) {
        return true;
      }
    }
    return false;
  }

  public static boolean isEmpty(Object[] array) {
    return !has(array);
  }

  /**
   * @return TRUE when given collection has elements; that is, it must not be {@code null} and must
   *     have at least one element. @return FALSE otherwise
   */
  public static boolean has(Collection<?> collection) {
    if (collection != null && !collection.isEmpty()) {
      return true;
    } else {
      return false;
    }
  }

  public static boolean isEmpty(Collection<?> collection) {
    return !has(collection);
  }

  /**
   * @return TRUE if given Map has entries; that is, it must not be {@code null} and must have at
   *     least one entry. Queue FALSE otherwise
   */
  public static boolean has(Map<?, ?> map) {
    if (map == null) {
      return false;
    }
    if (has(map.entrySet().toArray())) {
      return true;
    }
    return false;
  }

  public static boolean isEmpty(Map<?, ?> map) {
    return !has(map);
  }

  /**
   * Assert that an object is not {@code null}.
   *
   * <pre class="code">Assert.notNull(clazz, "The class must not be null");</pre>
   *
   * @param object the object to check
   * @param message the exception message to use if the assertion fails
   * @throws IllegalArgumentException if the object is {@code null}
   */
  public static void notNull(Object object, String message) {
    if (isEmpty(object)) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * Assert that an object is not {@code null}.
   *
   * <pre class="code">
   * Assert.notNull(clazz, () -&gt; "The class '" + clazz.getName() + "' must not be null");
   * </pre>
   *
   * @param object the object to check
   * @param messageSupplier a supplier for the exception message to use if the assertion fails
   * @throws IllegalArgumentException if the object is {@code null}
   */
  public static void notNull(Object object, Supplier<String> messageSupplier) {
    if (isEmpty(object)) {
      throw new IllegalArgumentException(nullSafeGet(messageSupplier));
    }
  }

  private static String nullSafeGet(Supplier<String> messageSupplier) {
    return (messageSupplier != null ? messageSupplier.get() : null);
  }
}
