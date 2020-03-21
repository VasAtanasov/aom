package bg.autohouse.data.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import bg.autohouse.data.models.Maker;
import java.util.List;
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
public class MakerRepositoryTest {
  @Autowired private MakerRepository makerRepository;

  @Test
  @Sql("/data.sql")
  void whenInitializedByDbUnit_thenFindsByName() {
    Maker maker = makerRepository.findByName("Audi").orElse(null);
    List<Maker> allMakers = makerRepository.findAll();

    assertThat(maker).isNotNull();
    assertThat(maker.getName()).isEqualTo("Audi");
    assertThat(allMakers).size().isEqualTo(135);
  }

  @Test
  void whenEmptyDb_thenFindsByName() {
    Maker maker = makerRepository.findByName("Audi").orElse(null);
    List<Maker> allMakers = makerRepository.findAll();

    assertThat(maker).isNull();
    assertThat(allMakers).size().isEqualTo(0);
  }
}
