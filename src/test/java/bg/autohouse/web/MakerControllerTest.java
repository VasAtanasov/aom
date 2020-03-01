package bg.autohouse.web;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bg.autohouse.BaseTest;
import bg.autohouse.data.models.Maker;
import bg.autohouse.data.repositories.MakerRepository;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class MakerControllerTest extends BaseTest {
  static final Maker maker = Maker.builder().name("Audi").build();

  @Autowired MakerRepository makerRepository;

  @Autowired Gson gson;

  @Before
  public void setUp() {
    Maker savedMaker = makerRepository.save(maker);
    log.info(gson.toJson(savedMaker));
  }

  @Test
  public void whenGetMakers_shouldReturn() throws Exception {
    mvcPerformer.performGet("/api/makers").andExpect(status().isOk());
  }

  @Test
  public void whenGetMakerId_shouldReturn() throws Exception {
    mvcPerformer
        .performGet("/api/makers/123")
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.success", is(false)));
  }
}
