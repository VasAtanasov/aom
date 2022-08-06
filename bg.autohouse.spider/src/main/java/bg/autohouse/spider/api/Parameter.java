package bg.autohouse.spider.api;

public interface Parameter<T> {
  String name();

  T value();

  default String getKey() {
    return this.name();
  }

  default T getValue() {
    return this.value();
  }

  default T setValue(T value) {
    throw new UnsupportedOperationException("Pair is read only");
  }
}
