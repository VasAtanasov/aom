package bg.autohouse.service.services.impl;

import static org.mockito.Mockito.*;

import bg.autohouse.data.models.enums.Feature;
import bg.autohouse.service.models.OfferTopServiceModel;
import bg.autohouse.service.services.OfferService;
import bg.autohouse.web.models.request.FilterRequest;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
@Sql("/data.sql")
public class OfferServiceImplTest {

  @Autowired private OfferService offerService;
  private Sort sort = Sort.by("createdAt").descending();
  private Pageable pageable = PageRequest.of(0, 20, sort);

  @Test
  void whenSearchOffers_withValidFilter_shouldMapCorrect() {
    FilterRequest filterRequest =
        FilterRequest.builder()
            // .fuelType(FuelType.GASOLINE.name())
            .feature(Arrays.asList(Feature.ABS.name(), Feature.LEATHER_STEERING_WHEEL.name(), null))
            .build();

    Page<OfferTopServiceModel> page = offerService.searchOffers(filterRequest, pageable);
    int a = 5;
  }
}
