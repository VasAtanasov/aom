package bg.autohouse.web.rest;

import bg.autohouse.data.models.Maker;
import bg.autohouse.data.models.Model;
import bg.autohouse.data.repositories.MakerRepository;
import bg.autohouse.data.repositories.ModelRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
public class HomeRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MakerRepository makerRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Before
    public void init() {
        Maker maker = Maker.builder()
                .id(1L)
                .name("bmw")
                .prettyName("BMW")
                .build();
        makerRepository.saveAndFlush(maker);

        Model x3 = Model.builder()
                .id(1L)
                .name("x3")
                .prettyName("X3")
                .build();
        Model x5 = Model.builder()
                .id(2L)
                .name("x3")
                .prettyName("X3")
                .build();

        List<Model> models = new ArrayList<>() {{
            add(x3);
            add(x5);
        }};

        models.forEach(maker::addModel);

        modelRepository.saveAndFlush(x3);
        modelRepository.saveAndFlush(x5);
    }

    @Test
    public void getModelsForMaker() throws Exception {
        mockMvc.perform(get("/api/makers/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"prettyName\":\"BMW\",\"name\":\"bmw\",\"models\":[{\"id\":1,\"name\":\"x3\",\"prettyName\":\"X3\"},{\"id\":2,\"name\":\"x3\",\"prettyName\":\"X3\"}]}"));
    }

    @Test
    public void getAllMakers_shouldReturnAll() throws Exception {
        mockMvc.perform(get("/api/makers/all"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"prettyName\":\"BMW\",\"name\":\"bmw\"}]"));
    }

    @Test
    public void getFuelTypes_shouldReturnAll_SortedAlphabetical() throws Exception {
        mockMvc.perform(get("/api/fuelTypes/all"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"CNG\":\"CNG\",\"Diesel\":\"DIESEL\",\"Electric\":\"ELECTRIC\",\"Electric/Diesel\":\"ELECTRIC_DIESEL\",\"Electric/Gasoline\":\"ELECTRIC_GASOLINE\",\"Ethanol\":\"ETHANOL\",\"Gasoline\":\"GASOLINE\",\"Hydrogen\":\"HYDROGEN\",\"LPG\":\"LPG\",\"Others\":\"OTHERS\"}"));
    }

    @Test
    public void getGears_shouldReturnAll_SortedAlphabetical() throws Exception {
        mockMvc.perform(get("/api/gears/all"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"Automatic\":\"AUTOMATIC\",\"Manual\":\"MANUAL\",\"Semi-automatic\":\"SEMI_AUTOMATIC\"}"));
    }
}