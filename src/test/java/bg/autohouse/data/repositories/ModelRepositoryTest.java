package bg.autohouse.data.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import bg.autohouse.data.models.Model;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Sql("/makersModelsTrims.sql")
@ActiveProfiles("test")
public class ModelRepositoryTest {
  @Autowired private ModelRepository modelRepository;

  @Test
  void whenFindByMakerId_shouldReturnModels() {
    List<Model> models = modelRepository.findAllByMakerId(2L).collect(Collectors.toList());
    assertThat(models).isNotEmpty();
  }

  @Test
  void whenFindByModelId_shouldReturnTrims() {
    List<Model> models = modelRepository.findAllByModelId(2L).collect(Collectors.toList());
    assertThat(models).isNotEmpty();
  }

  @Test
  void whenFindByModelId_shouldReturnModelYears() {
    List<Object[]> modelsYears =
        modelRepository.findAllYearsByModelId(2L).collect(Collectors.toList());
    assertThat(modelsYears).isNotEmpty();
  }

  @Test
  void whenFindByModelIdAndModelYear_shouldReturnModelYearTrims() {
    List<Model> modelsYears =
        modelRepository.findAllByModelIdAndModelYear(2L, 2020).collect(Collectors.toList());
    assertThat(modelsYears).isNotEmpty();
  }
}
