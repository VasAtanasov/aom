package bg.autohouse.data.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class ModelRepositoryTest {
  @Autowired private ModelRepository modelRepository;

  @Test
  @Sql("test-data.sql")
  void whenInitializedByDbUnit_thenFindsByName() {
    boolean exists = modelRepository.existsByNameAndMakerId("Q5", 8L);
    assertThat(exists).isTrue();
  }
}
