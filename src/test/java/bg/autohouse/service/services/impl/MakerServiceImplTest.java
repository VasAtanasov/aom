package bg.autohouse.service.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import bg.autohouse.SingletonModelMapper;
import bg.autohouse.data.models.Maker;
import bg.autohouse.data.repositories.MakerRepository;
import bg.autohouse.data.repositories.ModelRepository;
import bg.autohouse.service.models.MakerServiceModel;
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

  @Test
  @ParameterizedTest
  @MethodSource("validMares")
  void whenFindById_withExistingMaker_returnsCorrectlyMappedObject(Maker maker) {

    when(makerRepository.findById(maker.getId())).thenReturn(Optional.of(maker));

    MakerServiceModel result = makerService.getOne(maker.getId());

    assertThat(result).isNotNull();
  }

  private static Stream<Maker> validMares() {
    return Stream.of(
        Maker.builder().id(1L).name("Audi").build(), Maker.builder().id(2L).name("BMW").build());
  }
}
