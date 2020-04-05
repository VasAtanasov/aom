package bg.autohouse.data.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import bg.autohouse.data.models.Model;
import bg.autohouse.data.projections.ModelProjection;
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
  void whenGetMakerModelsYears_shouldReturnModelsWithYears() {
    List<ModelProjection> models =
        modelRepository.findMakerModelsYears(2L).collect(Collectors.toList());

    int a = 5;
    assertThat(models).isNotEmpty();
  }
}
