package bg.autohouse.service.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

import bg.autohouse.SingletonModelMapper;
import bg.autohouse.data.models.Maker;
import bg.autohouse.data.repositories.MakerRepository;
import bg.autohouse.data.repositories.ModelRepository;
import bg.autohouse.errors.ExceptionsMessages;
import bg.autohouse.errors.MakerNotFoundException;
import bg.autohouse.errors.ResourceAlreadyExistsException;
import bg.autohouse.service.models.MakerModelServiceModel;
import bg.autohouse.service.models.MakerServiceModel;
import bg.autohouse.service.models.ModelServiceModel;
import bg.autohouse.service.services.MakerService;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.util.ModelMapperWrapperImpl;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class MakerServiceImplTest {

  private MakerRepository makerRepository;
  private ModelRepository modelRepository;
  private MakerService makerService;
  private ModelMapperWrapper modelMapper =
      new ModelMapperWrapperImpl(SingletonModelMapper.mapper());

  @BeforeEach
  void setUp() {
    makerRepository = mock(MakerRepository.class);
    modelRepository = mock(ModelRepository.class);
    makerService = new MakerServiceImpl(makerRepository, modelRepository, modelMapper);
  }

  @ParameterizedTest
  @MethodSource("validMares")
  void whenFindById_withExistingMaker_returnsCorrectlyMappedObject(Maker maker) {

    when(makerRepository.findById(maker.getId())).thenReturn(Optional.of(maker));

    MakerModelServiceModel result = makerService.getOne(maker.getId());

    assertThat(result).isNotNull();
  }

  private static Stream<Maker> validMares() {
    return Stream.of(
        Maker.builder().id(1L).name("Audi").build(), Maker.builder().id(2L).name("BMW").build());
  }

  @Test
  void whenFindById_withNonExistingMaker_shouldThrow() {

    Throwable thrown =
        catchThrowable(
            () -> {
              makerService.getOne(1L);
            });

    assertThat(thrown)
        .isInstanceOf(MakerNotFoundException.class)
        .hasMessage(ExceptionsMessages.EXCEPTION_MAKER_NOT_FOUND);
  }

  @Test
  void whenAddModelToMaker_withNonExistingMakerId_shouldAThrow() {
    Throwable thrown =
        catchThrowable(
            () -> {
              makerService.addModelToMaker(1L, ModelServiceModel.of(1L, "name", 1L));
            });

    assertThat(thrown)
        .isInstanceOf(MakerNotFoundException.class)
        .hasMessage(ExceptionsMessages.EXCEPTION_MAKER_NOT_FOUND);
  }

  @Test
  void whenAddModelToMaker_withExistingModelName_shouldThrow() {
    Maker maker = Maker.builder().id(1L).name("Audi").build();
    ModelServiceModel model = ModelServiceModel.of(1L, "Q5", maker.getId());

    when(makerRepository.findById(anyLong())).thenReturn(Optional.of(maker));
    when(modelRepository.existsByNameAndMakerId(anyString(), anyLong())).thenReturn(true);

    Throwable thrown =
        catchThrowable(
            () -> {
              makerService.addModelToMaker(maker.getId(), model);
            });

    assertThat(thrown)
        .isInstanceOf(ResourceAlreadyExistsException.class)
        .hasMessage(ExceptionsMessages.MODEL_WITH_NAME_EXISTS);
  }

  @Test
  void whenAddModelToMaker_withValidData_shouldAdd() {
    Maker maker = Maker.builder().id(1L).name("Audi").build();
    ModelServiceModel model = ModelServiceModel.of(1L, "Q5", maker.getId());

    when(makerRepository.findById(anyLong())).thenReturn(Optional.of(maker));
    when(modelRepository.existsByNameAndMakerId(anyString(), anyLong())).thenReturn(false);

    MakerServiceModel updatedMaker = makerService.addModelToMaker(maker.getId(), model);

    assertThat(updatedMaker).isNotNull();
  }
}
