package bg.autohouse.data.repositories;


public interface BatchRepository<T, ID> {

  <S extends T> void saveInBatch(Iterable<S> entities);
}
