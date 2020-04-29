package bg.autohouse.service.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import bg.autohouse.data.models.Maker;
import bg.autohouse.errors.MakerNotFoundException;
import bg.autohouse.errors.ResourceAlreadyExistsException;
import bg.autohouse.service.models.MakerModelServiceModel;
import bg.autohouse.service.models.MakerServiceModel;
import bg.autohouse.service.models.ModelServiceModel;
import bg.autohouse.service.models.ModelTrimsServicesModel;
import bg.autohouse.service.services.MakerService;
import bg.autohouse.web.enums.RestMessage;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql("/makersModelsTrims.sql")
@TestPropertySource("classpath:test.properties")
@Transactional
public class MakerServiceImplTest {

  @Autowired private MakerService makerService;

  @Test
  void whenGetMaker_byId_shouldReturn() {
    MakerModelServiceModel maker = makerService.getOne(1L);
    assertThat(maker).isNotNull();
  }

  @Test
  void whenFindById_withNonExistingMaker_shouldThrow() {

    Throwable thrown =
        catchThrowable(
            () -> {
              makerService.getOne(1000L);
            });

    assertThat(thrown)
        .isInstanceOf(MakerNotFoundException.class)
        .hasMessage(RestMessage.MAKER_NOT_FOUND.name());
  }

  @Test
  void whenAddModelToMaker_withNonExistingMakerId_shouldAThrow() {
    Throwable thrown =
        catchThrowable(
            () -> {
              makerService.addModelToMaker(1000L, ModelServiceModel.of(1L, "name", 1000L));
            });

    assertThat(thrown)
        .isInstanceOf(MakerNotFoundException.class)
        .hasMessage(RestMessage.MAKER_NOT_FOUND.name());
  }

  @Test
  void whenAddModelToMaker_withExistingModelName_shouldThrow() {
    ModelServiceModel model = ModelServiceModel.builder().name("ILX").makerId(2L).build();

    Throwable thrown =
        catchThrowable(
            () -> {
              makerService.addModelToMaker(2L, model);
            });

    assertThat(thrown)
        .isInstanceOf(ResourceAlreadyExistsException.class)
        .hasMessage(RestMessage.MODEL_ALREADY_EXISTS.name());
  }

  @Test
  void whenAddModelToMaker_withValidData_shouldAdd() {
    Maker maker = Maker.builder().id(1L).name("Audi").build();
    ModelServiceModel model = ModelServiceModel.of(1L, "Q5", maker.getId());

    MakerServiceModel updatedMaker = makerService.addModelToMaker(maker.getId(), model);

    assertThat(updatedMaker).isNotNull();
  }

  @Test
  void whenFindModelsForMaker_shouldReturnAllModels() {
    List<ModelTrimsServicesModel> models = makerService.getMakerModelsTrims(2L);
    assertThat(models).isNotNull();
  }

  @Test
  void whenGetAllMakers_shouldReturnAll() {
    List<MakerModelServiceModel> makers = makerService.getAllMakerWithModels();
    assertThat(makers).isNotNull();
  }
}
