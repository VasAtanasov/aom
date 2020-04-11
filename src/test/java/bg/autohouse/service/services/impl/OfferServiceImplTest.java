package bg.autohouse.service.services.impl;

import static org.assertj.core.api.Assertions.assertThat;

import bg.autohouse.data.models.Offer;
import bg.autohouse.data.models.enums.FuelType;
import bg.autohouse.data.repositories.OfferRepository;
import bg.autohouse.service.models.OfferServiceModel;
import bg.autohouse.service.services.OfferService;
import bg.autohouse.web.models.request.FilterRequest;
import java.util.List;
import java.util.stream.Collectors;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
// @Sql("/data.sql")
@Transactional
@TestPropertySource("classpath:test.properties")
public class OfferServiceImplTest {

  @Autowired private OfferService offerService;
  @Autowired private OfferRepository offerRepository;

  private Sort sort = Sort.by("createdAt").descending();
  private Pageable pageable = PageRequest.of(0, 20, sort);

  @Test
  void whenSearchOffers_withValidFilter_shouldMapCorrect() {
    FilterRequest filterRequest =
        FilterRequest.builder().fuelType(FuelType.GASOLINE.name()).build();

    Page<OfferServiceModel> page = offerService.searchOffers(filterRequest, pageable);
    List<Offer> offers =
        offerRepository.findAll().stream()
            .filter(offer -> offer.getVehicle().getEngine().getFuelType().equals(FuelType.GASOLINE))
            .collect(Collectors.toList());

    assertThat(page.getContent()).size().isEqualTo(offers.size());
  }

  @Test
  void whenSearchOffers_withValidMakerId_shouldMapCorrect() {
    FilterRequest filterRequest = FilterRequest.builder().makerId(1L).build();

    Page<OfferServiceModel> page = offerService.searchOffers(filterRequest, pageable);
    List<Offer> offers =
        offerRepository.findAll().stream()
            .filter(offer -> offer.getVehicle().getMaker().getId().equals(1L))
            .collect(Collectors.toList());

    assertThat(page.getContent()).size().isEqualTo(offers.size());
  }

  @Test
  void whenSearchOffers_withValidModelId_shouldMapCorrect() {
    FilterRequest filterRequest = FilterRequest.builder().makerId(87L).modelId(1505L).build();

    Page<OfferServiceModel> page = offerService.searchOffers(filterRequest, pageable);
    List<Offer> offers =
        offerRepository.findAll().stream()
            .filter(offer -> offer.getVehicle().getModel().getId().equals(1505L))
            .collect(Collectors.toList());

    assertThat(page.getContent()).size().isEqualTo(offers.size());
  }
}
