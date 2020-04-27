package bg.autohouse.data.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import bg.autohouse.data.models.Maker;
import java.util.List;
import java.util.Optional;
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
@Sql("/makersModelsTrims.sql")
public class MakerRepositoryTest {
  @Autowired private MakerRepository makerRepository;

  @Test
  void whenInitializedByDbUnit_thenFindsByName() {
    Maker maker = makerRepository.findByName("Audi").orElse(null);
    assertThat(maker).isNotNull();
    assertThat(maker.getName()).isEqualTo("Audi");
  }

  @Test
  void whenNotExistingMaker_thenFindsByName_shouldReturnNull() {
    Maker maker = makerRepository.findByName("Not Existing Maker").orElse(null);
    assertThat(maker).isNull();
  }

  @Test
  void whenFindMakersWithModels_ShouldReturnAll() {
    List<Maker> makers = makerRepository.findAllWithModels();
    assertThat(makers).isNotEmpty();
  }

  @Test
  void whenFindById_ShouldReturnAll() {
    Optional<Maker> maker = makerRepository.findById(2L);
    assertThat(maker).isNotEmpty();
  }
}
