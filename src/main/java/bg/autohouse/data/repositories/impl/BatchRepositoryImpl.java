package bg.autohouse.data.repositories.impl;

import bg.autohouse.data.repositories.BatchRepository;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.Dialect;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BatchRepositoryImpl<T, ID> implements BatchRepository<T, ID> {

  @PersistenceContext private final EntityManager entityManager;

  @Override
  @Transactional
  public <S extends T> void saveInBatch(Iterable<S> entities) {

    if (entities == null) {
      throw new IllegalArgumentException("The given Iterable of entities cannot be null!");
    }

    int i = 0;
    for (S entity : entities) {
      entityManager.persist(entity);

      i++;

      // Flush a batch of inserts and release memory
      if (i % batchSize() == 0 && i > 0) {
        log.info("Flushing the EntityManager containing {0} entities ...", i);

        entityManager.flush();
        entityManager.clear();
        i = 0;
      }
    }

    if (i > 0) {
      log.info("Flushing the remaining {0} entities ...", i);

      entityManager.flush();
      entityManager.clear();
    }
  }

  private static int batchSize() {

    int batchsize = Integer.valueOf(Dialect.DEFAULT_BATCH_SIZE); // default batch size

    Properties configuration = new Properties();
    try (InputStream inputStream =
        BatchRepositoryImpl.class.getClassLoader().getResourceAsStream("application.properties")) {
      configuration.load(inputStream);
    } catch (IOException ex) {
      log.error("Cannot fetch batch size. Using further Dialect.DEFAULT_BATCH_SIZE{0}", ex);
      return batchsize;
    }

    String batchsizestr =
        configuration.getProperty("spring.jpa.properties.hibernate.jdbc.batch_size");
    if (batchsizestr != null) {
      batchsize = Integer.valueOf(batchsizestr);
    }

    return batchsize;
  }
}
