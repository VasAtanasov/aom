package bg.autohouse.util;

import static java.util.stream.Collectors.toSet;

import com.google.common.collect.Sets;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class ReflectionUtil {

  private static final boolean DEFAULT_INCLUDE_JAVA_LANG_OBJECT = false;

  // Predicates

  public static final Predicate<Class<?>> ABSTRACT_OR_CONCRETE_CLASS =
      clazz ->
          clazz != null
              && !clazz.isInterface()
              && !clazz.isAnonymousClass()
              && !clazz.isEnum()
              && !Throwable.class.isAssignableFrom(clazz);

  // Methods returning functions

  @Nonnull
  public static Function<Class<?>, Stream<Field>> fieldsOfClass(
      final boolean includeSuperTypeFields) {
    return clazz ->
        clazz == null
            ? Stream.empty()
            : (includeSuperTypeFields ? getAllFields(clazz) : getFields(clazz)).stream();
  }

  @Nonnull
  public static Function<Class<?>, Stream<Method>> methodsOfClass(
      final boolean includeSuperTypeMethods) {
    return clazz ->
        clazz == null
            ? Stream.empty()
            : (includeSuperTypeMethods ? getAllMethods(clazz) : getMethods(clazz)).stream();
  }

  @Nonnull
  public static Set<Field> getFields(final Class<?> type) {
    return Sets.newHashSet(Objects.requireNonNull(type).getDeclaredFields());
  }

  @Nonnull
  public static Set<Field> getAllFields(final Class<?> type) {
    return getAllSuperTypes(type).stream()
        .flatMap(clazz -> getFields(clazz).stream())
        .collect(toSet());
  }

  @Nonnull
  public static Set<Method> getMethods(final Class<?> type) {
    Objects.requireNonNull(type);
    return Sets.newHashSet(type.isInterface() ? type.getMethods() : type.getDeclaredMethods());
  }

  @Nonnull
  public static Set<Method> getAllMethods(final Class<?> type) {
    return getAllMethods(type, DEFAULT_INCLUDE_JAVA_LANG_OBJECT);
  }

  @Nonnull
  public static Set<Method> getAllMethods(
      final Class<?> type, final boolean includeJavaLangObject) {
    return getAllSuperTypes(type, includeJavaLangObject).stream()
        .flatMap(clazz -> getMethods(clazz).stream())
        .collect(toSet());
  }

  @Nonnull
  public static Set<Class<?>> getAllSuperTypes(final Class<?> type) {
    return getAllSuperTypes(type, DEFAULT_INCLUDE_JAVA_LANG_OBJECT);
  }

  @Nonnull
  public static Set<Class<?>> getAllSuperTypes(
      final Class<?> type, final boolean includeJavaLangObject) {
    if (type == null || !includeJavaLangObject && type.equals(Object.class)) {
      return Collections.emptySet();
    }

    return Stream.concat(
            F.stream(type, getAllSuperTypes(type.getSuperclass(), includeJavaLangObject)),
            Stream.of(type.getInterfaces())
                .flatMap(ifc -> getAllSuperTypes(ifc, includeJavaLangObject).stream()))
        .collect(toSet());
  }
}
