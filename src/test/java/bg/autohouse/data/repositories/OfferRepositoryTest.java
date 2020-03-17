package bg.autohouse.data.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import bg.autohouse.data.models.Filter;
import bg.autohouse.data.models.Offer;
import bg.autohouse.data.models.enums.FuelType;
import bg.autohouse.data.specifications.OfferSpecifications;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class OfferRepositoryTest {
  private static final int DEFAULT_SIZE = 20;

  private static final Sort DEFAULT_SORT = Sort.by("createdAt").descending();
  private static final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, DEFAULT_SIZE, DEFAULT_SORT);

  @Autowired private OfferRepository offerRepository;

  @Test
  @Sql("test-data.sql")
  void whenFindLatestOffers_shouldReturnCollection() {

    Stream<Offer> offers = offerRepository.findLatestOffers(DEFAULT_PAGEABLE);
    Page<Offer> offersPage = offerRepository.findLatestOffersPage(DEFAULT_PAGEABLE);

    assertThat(offers).size().isEqualTo(DEFAULT_SIZE);
    assertThat(offersPage.getTotalElements()).isEqualTo(100);
  }

  @Test
  @Sql("test-data.sql")
  void whenOfferFilter_byFuelType_shouldReturnCollection() {
    Filter filter = Filter.builder().fuelType(FuelType.GASOLINE).build();
    long count = offerRepository.count();
    assertThat(count).isEqualTo(100);

    Specification<Offer> offerSpecification = OfferSpecifications.getOffersByFilter(filter);

    List<Offer> offers = offerRepository.findAll(offerSpecification);
    assertThat(offers)
        .allMatch(offer -> offer.getVehicle().getEngine().getFuelType().equals(FuelType.GASOLINE));
  }
}
