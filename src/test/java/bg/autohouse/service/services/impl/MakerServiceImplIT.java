package bg.autohouse.service.services.impl;

import static org.assertj.core.api.Assertions.assertThat;

import bg.autohouse.service.models.MakerModelServiceModel;
import bg.autohouse.service.services.MakerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
@Sql("/data.sql")
@TestPropertySource("classpath:test.properties")
public class MakerServiceImplIT {

  @Autowired private MakerService makerService;

  @Test
  void whenGetMaker_byId_shouldReturn() {
    MakerModelServiceModel maker = makerService.getOne(1L);
    assertThat(maker).isNotNull();
  }
}
