package bg.autohouse.data.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import bg.autohouse.data.models.Trim;
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
public class TrimRepositoryTest {
  @Autowired private TrimRepository trimRepository;

  @Test
  void whenFindTrims_byModelId_Trims() {
    List<Trim> trims = trimRepository.findAllByModelId(2L).collect(Collectors.toList());
    assertThat(trims).isNotEmpty();
  }
}
