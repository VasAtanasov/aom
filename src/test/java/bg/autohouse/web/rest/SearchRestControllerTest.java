package bg.autohouse.web.rest;

import bg.autohouse.data.models.Feature;
import bg.autohouse.data.models.Location;
import bg.autohouse.data.repositories.FeatureRepository;
import bg.autohouse.data.repositories.LocationRepository;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
public class SearchRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Before
    public void init() {
        AtomicLong featureId = new AtomicLong(0);

        List<Feature> features = List.of(
                "ABS",
                "Driver-side airbag",
                "Passenger-side airbag",
                "Sunroof",
                "Radio",
                "4WD",
                "Power windows",
                "Alloy wheels",
                "Central door lock",
                "Alarm system")
                .stream()
                .map(s -> Feature.builder().id(featureId.incrementAndGet()).name(s).build())
                .collect(Collectors.toUnmodifiableList());

        featureRepository.saveAll(features);
        featureRepository.flush();

        AtomicLong locationId = new AtomicLong(0);

        List<Location> towns = List.of(
                "Sofia",
                "Plovdiv",
                "Varna")
                .stream()
                .map(s -> Location.builder().id(locationId.incrementAndGet()).name(s).build())
                .collect(Collectors.toUnmodifiableList());

        locationRepository.saveAll(towns);
        locationRepository.flush();
    }

    @Test
    public void getAllFeatures_when10Features_10Features() throws Exception {
        getResultAction(
                get("/api/features/all"),
                "[{\"id\":1,\"name\":\"ABS\"},{\"id\":2,\"name\":\"Driver-side airbag\"},{\"id\":3,\"name\":\"Passenger-side airbag\"},{\"id\":4,\"name\":\"Sunroof\"},{\"id\":5,\"name\":\"Radio\"},{\"id\":6,\"name\":\"4WD\"},{\"id\":7,\"name\":\"Power windows\"},{\"id\":8,\"name\":\"Alloy wheels\"},{\"id\":9,\"name\":\"Central door lock\"},{\"id\":10,\"name\":\"Alarm system\"}]"
        );
    }

    @Test
    public void getAllBodYStyles_whenGet_alphabeticalOrderBodyStyle() throws Exception {
        getResultAction(
                get("/api/bodyStyles/all"),
                "{\"Convertible\":\"CONVERTIBLE\",\"Coupe\":\"COUPE\",\"Crossover\":\"CROSSOVER\",\"Hatchback\":\"HATCHBACK\",\"Luxury\":\"LUXURY\",\"Minivan\":\"MINIVAN\",\"MVP\":\"MPV\",\"Other\":\"OTHER\",\"Pickup\":\"PICKUP\",\"Sedan\":\"SEDAN\",\"Sport\":\"SPORT\",\"SUV\":\"SUV\",\"Truck\":\"TRUCK\",\"Van\":\"VAN\",\"Wagon\":\"WAGON\"}"
        );
    }

    @Test
    public void getAllDriveTrains() throws Exception {
        getResultAction(
                get("/api/drivetrain/all"),
                "{\"All Wheel Drive\":\"ALL_WHEEL_DRIVE\",\"Four Wheel Drive\":\"FOUR_WHEEL_DRIVE\",\"Front Wheel Drive\":\"FRONT_WHEEL_DRIVE\",\"Rear Wheel Drive\":\"REAR_WHEEL_DRIVE\"}"
        );
    }

    @Test
    public void getAllSortTypes() throws Exception {
        getResultAction(
                get("/api/orderBy/all"),
                "{\"First registration ascending\":\"REGISTRATION_ASC\",\"First registration descending\":\"REGISTRATION_DSC\",\"Latest offers first\":\"LATEST\",\"Mileage ascending\":\"MILEAGE_ASC\",\"Mileage descending\":\"MILEAGE_DSC\",\"Power ascending\":\"POWER_ASC\",\"Power descending\":\"POWER_DSC\",\"Price ascending\":\"PRICE_ASC\",\"Price descending\":\"PRICE_DSC\"}"
        );
    }

    @Test
    public void getAllEuroStandards() throws Exception {
        getResultAction(
                get("/api/euroStandard/all"),
                "{\"Euro 1\":\"EURO1\",\"Euro 2\":\"EURO2\",\"Euro 3\":\"EURO3\",\"Euro 4\":\"EURO4\",\"Euro 5\":\"EURO5\",\"Euro 6\":\"EURO6\"}"
        );
    }

    @Test
    public void getAllLocations() throws Exception {
        getResultAction(
                get("/api/locations/all"),
                "[{\"id\":1,\"name\":\"Sofia\"},{\"id\":2,\"name\":\"Plovdiv\"},{\"id\":3,\"name\":\"Varna\"}]"
        );
    }

    @Test
    public void getAllColors() throws Exception {
        getResultAction(
                get("/api/colors/all"),
                "{\"Black\":\"BLACK\",\"Cream\":\"CREAM\",\"Dark Blue\":\"DARK_BLUE\",\"Dark Brown\":\"DARK_BROWN\",\"Dark Green\":\"DARK_GREEN\",\"Dark Red\":\"DARK_RED\",\"Gold\":\"GOLD\",\"Gray\":\"GRAY\",\"Light Blue\":\"LIGHT_BLUE\",\"Light Brown\":\"LIGHT_BROWN\",\"Light Green\":\"LIGHT_GREEN\",\"Orange\":\"ORANGE\",\"Purple\":\"PURPLE\",\"Red\":\"RED\",\"Silver\":\"SILVER\",\"White\":\"WHITE\",\"Yellow\":\"YELLOW\"}"
        );
    }

    private ResultActions getResultAction(MockHttpServletRequestBuilder requestBuilder, String expectedJsonObj) throws Exception {
        return mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonObj));
    }
}