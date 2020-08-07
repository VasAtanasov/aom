package bg.autohouse.service.services.impl;

import bg.autohouse.service.models.InitialStateModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class InitialStateServiceImplTest {

  @InjectMocks InitialStateServiceImpl initialStateService;

  @Test
  void when_getInitialSate_shouldReturn() {
    InitialStateModel model = initialStateService.getInitialState();
    assertThat(model.getSearchCriteriaNamesForCheckboxCriteria()).isNotEmpty();
    assertThat(model.getSearchCriteriaNamesForRangeCriteria()).isNotEmpty();
    assertThat(model.getSearchCriteriaNamesForSelectCriteria()).isNotEmpty();
  }
}
