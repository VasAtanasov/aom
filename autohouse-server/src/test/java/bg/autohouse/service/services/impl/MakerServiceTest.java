package bg.autohouse.service.services.impl;

import bg.autohouse.data.models.Maker;
import bg.autohouse.data.models.Model;
import bg.autohouse.data.repositories.MakerRepository;
import bg.autohouse.data.repositories.ModelRepository;
import bg.autohouse.service.models.MakerNameServiceModel;
import bg.autohouse.service.models.MakerServiceModel;
import bg.autohouse.service.services.MakerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MakerServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private MakerRepository makerRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private MakerService makerService;

    @Before
    public void init() {
        Maker maker = Maker.builder()
                .name("bmw")
                .prettyName("BMW")
                .build();
        makerRepository.saveAndFlush(maker);

        Model x3 = Model.builder()
                .name("x3")
                .prettyName("X3")
                .build();
        Model x5 = Model.builder()
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
    public void getAllMakers_whenOneCar_OneCar() {
        List<MakerNameServiceModel> resultList = makerService.getAllMakers();
        assertThat(resultList.size(), is(1));
        assertThat(resultList.get(0).getName(), is("bmw"));
    }

    @Test
    public void getModelsForMaker_whenId1_isMaker() {
        MakerServiceModel maker = makerService.getModelsForMaker(1L);
        assertThat(maker.getId(), is(1L));
        assertThat(maker.getModels().size(), is(2));
    }
}