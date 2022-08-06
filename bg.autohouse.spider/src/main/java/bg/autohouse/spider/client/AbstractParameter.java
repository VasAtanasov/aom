package bg.autohouse.spider.client;

import bg.autohouse.spider.api.Parameter;
import java.io.Serializable;
import java.util.Objects;

public class AbstractParameter<T> implements Parameter<T>, Serializable {
  private final String name;
  private final T value;

  public AbstractParameter(String key, T value) {
    this.name = Objects.requireNonNull(key);
    this.value = Objects.requireNonNull(value);
  }

  public String name() {
    return this.name;
  }

  public T value() {
    return this.value;
  }
}
