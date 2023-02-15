package bg.autohouse.api.dto;

public interface HasID<T> {
  T getId();

  void setId(T id);
}
