package bg.autohouse.web;

import static org.hamcrest.CoreMatchers.is;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bg.autohouse.BaseTest;
import bg.autohouse.common.Constants;
import bg.autohouse.data.models.Maker;
import bg.autohouse.data.repositories.MakerRepository;
import bg.autohouse.util.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
public class MakerControllerTest extends BaseTest {
  static final String MAKER_NAME = "Audi";

  static final Maker maker = Maker.builder().name(MAKER_NAME).build();

  @Autowired MakerRepository makerRepository;
  @Autowired JsonParser jsonParser;

  @Before
  public void setUp() {
    Maker savedMaker = makerRepository.save(maker);
    log.info(jsonParser.toString(savedMaker));
  }

  @Test
  public void whenGetMakers_shouldReturn() throws Exception {
    mvcPerformer.performGet("/api/makers").andExpect(status().isOk());
  }

  @Test
  public void whenGetMaker_notExistingId_shouldReturnFalse() throws Exception {
    mvcPerformer
        .performGet("/api/makers/123")
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.success", is(false)))
        .andExpect(jsonPath("$.message", is(Constants.EXCEPTION_MAKER_NOT_FOUND)))
        .andExpect(jsonPath("$.status", is("Not Found")));
  }
}
