package bg.autohouse.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class ClassUtils {

  private static final char PKG_SEPARATOR = '.';

  private static final char DIR_SEPARATOR = '/';

  private static final String CLASS_FILE_SUFFIX = ".class";

  private static final String BAD_PACKAGE_ERROR =
      "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

  public static List<Class<?>> find(String scannedPackage) {
    String scannedPath = scannedPackage.replace(PKG_SEPARATOR, DIR_SEPARATOR);
    URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
    if (scannedUrl == null) {
      throw new IllegalArgumentException(
          String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage));
    }
    File scannedDir = new File(scannedUrl.getFile());
    List<Class<?>> classes = new ArrayList<Class<?>>();
    for (File file : scannedDir.listFiles()) {
      classes.addAll(find(file, scannedPackage));
    }
    return classes;
  }

  private static List<Class<?>> find(File file, String scannedPackage) {
    List<Class<?>> classes = new ArrayList<Class<?>>();
    String resource = scannedPackage + PKG_SEPARATOR + file.getName();
    if (file.isDirectory()) {
      for (File child : file.listFiles()) {
        classes.addAll(find(child, resource));
      }
    } else if (resource.endsWith(CLASS_FILE_SUFFIX)) {
      int endIndex = resource.length() - CLASS_FILE_SUFFIX.length();
      String className = resource.substring(0, endIndex);
      try {
        classes.add(Class.forName(className));
      } catch (ClassNotFoundException ignore) {
      }
    }
    return classes;
  }

  public static Class<?> resolveGenericType(@Nonnull final Field field) {
    Objects.requireNonNull(field);
    final Type[] actualTypeArgs =
        ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
    return actualTypeArgs.length == 1 ? (Class<?>) actualTypeArgs[0] : null;
  }

  @Nonnull
  public static Class<?> getTypeArgumentOfSuperClass(
      @Nonnull final Object object,
      @Nonnull final Class<?> superClass,
      final int typeArgumentIndex) {

    Objects.requireNonNull(object, "object is null");
    Objects.requireNonNull(superClass, "superClass is null");
    Preconditions.checkArgument(typeArgumentIndex >= 0, "typeArgumentIndex must not be negative");

    final Optional<ParameterizedType> paramType =
        findParameterizedParentTypeOfClass(object.getClass(), superClass);

    if (!paramType.isPresent()) {
      throw new IllegalArgumentException(
          String.format(
              "Did not find parameterized interface of class %s for object of class %s",
              superClass.getName(), object.getClass().getName()));
    }

    final Type[] typeArguments = paramType.get().getActualTypeArguments();

    if (typeArguments.length == 0) {
      throw new IllegalArgumentException(
          "The interface does not have type arguments: " + superClass.getName());
    }

    return (Class<?>) typeArguments[typeArgumentIndex];
  }

  @Nonnull
  public static Optional<ParameterizedType> findParameterizedParentTypeOfClass(
      @Nonnull final Class<?> clazz, @Nonnull final Class<?> expectedType) {

    Objects.requireNonNull(clazz, "clazz is null");
    Objects.requireNonNull(expectedType, "expectedType is null");

    final Type genericSuperclass = clazz.getGenericSuperclass();
    final Type[] genericInterfaces = clazz.getGenericInterfaces();

    for (final Type type : Lists.asList(genericSuperclass, genericInterfaces)) {
      if (type != null) {
        if (type instanceof ParameterizedType) {
          final ParameterizedType parameterizedType = (ParameterizedType) type;
          final Type rawType = parameterizedType.getRawType();

          if (rawType instanceof Class) {
            final Class<?> rawClass = (Class<?>) rawType;

            if (expectedType.equals(rawClass)) {
              return Optional.of(parameterizedType);
            }

            final Optional<ParameterizedType> match =
                findParameterizedParentTypeOfClass(rawClass, expectedType);
            if (match.isPresent()) {
              return match;
            }
          }
        } else if (type instanceof Class) {
          final Optional<ParameterizedType> match =
              findParameterizedParentTypeOfClass((Class<?>) type, expectedType);
          if (match.isPresent()) {
            return match;
          }
        }
      }
    }

    return Optional.empty();
  }

  public static boolean isRuntimeException(@Nonnull final Class<?> clazz) {
    return RuntimeException.class.isAssignableFrom(clazz);
  }

  public static boolean isCheckedException(@Nonnull final Class<?> clazz) {
    return Exception.class.isAssignableFrom(clazz) && !isRuntimeException(clazz);
  }

  @Nonnull
  public static <T> Optional<T> cast(final Object obj, @Nonnull final Class<T> refClass) {
    Objects.requireNonNull(refClass, "refClass is null");

    return Optional.ofNullable(
        obj != null && refClass.isAssignableFrom(obj.getClass()) ? refClass.cast(obj) : null);
  }
}
