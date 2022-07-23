package bg.autohouse.spider.client;

import bg.autohouse.spider.client.api.Param;

import java.io.Serializable;
import java.util.Objects;

public class Parameter<T> implements Param<T>, Serializable {
  private final String name;
  private final T value;

  public Parameter(String key, T value) {
    this.name = Objects.requireNonNull(key);
    this.value = Objects.requireNonNull(value);
  }

  public static <V> Parameter<V> of(String key, V value) {
    return new Parameter<>(key, value);
  }

  public String name() {
    return this.name;
  }

  public T value() {
    return this.value;
  }
}