package bg.autohouse.data.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.jpa.domain.Specification.where;

import bg.autohouse.data.models.Filter;
import bg.autohouse.data.models.enums.AccountType;
import bg.autohouse.data.models.enums.BodyStyle;
import bg.autohouse.data.models.enums.Feature;
import bg.autohouse.data.models.enums.FuelType;
import bg.autohouse.data.models.enums.State;
import bg.autohouse.data.models.offer.Offer;
import bg.autohouse.data.specifications.OfferSpecifications;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class OfferRepositoryTest {

  @Autowired private OfferRepository offerRepository;

  @Test
  void whenOfferFilter_byFuelType_shouldReturnCollection() {
    Filter filter = Filter.builder().fuelType(FuelType.GASOLINE).build();

    Specification<Offer> offerSpecification = where(OfferSpecifications.getOffersByFilter(filter));

    List<Offer> offers = offerRepository.findAll(offerSpecification);

    assertThat(offers)
        .allMatch(offer -> offer.getVehicle().getFuelType().equals(FuelType.GASOLINE));
  }

  @Test
  void whenOfferFilter_byBodyStyle_shouldReturnCollection() {
    Filter filter = Filter.builder().bodyStyle(BodyStyle.SUV).build();

    Specification<Offer> offerSpecification = where(OfferSpecifications.getOffersByFilter(filter));

    List<Offer> offers = offerRepository.findAll(offerSpecification);

    assertThat(offers).allMatch(offer -> offer.getVehicle().getBodyStyle().equals(BodyStyle.SUV));
  }

  @Test
  void whenOfferFilter_byState_shouldReturnCollection() {
    List<State> state = Arrays.asList(State.NEW, State.USED);
    Filter filter = Filter.builder().state(state).build();

    Specification<Offer> offerSpecification = where(OfferSpecifications.getOffersByFilter(filter));

    List<Offer> offers = offerRepository.findAll(offerSpecification);

    assertThat(offers).allMatch(offer -> state.contains(offer.getVehicle().getState()));
  }

  @Test
  void whenOfferFilter_bySeller_shouldReturnCollection() {
    List<AccountType> accountTypes = Arrays.asList(AccountType.DEALER);
    Filter filter = Filter.builder().seller(accountTypes).build();

    Specification<Offer> offerSpecification = where(OfferSpecifications.getOffersByFilter(filter));

    List<Offer> offers = offerRepository.findAll(offerSpecification);

    assertThat(offers)
        .allMatch(offer -> accountTypes.contains(offer.getAccount().getAccountType()));
  }

  @Test
  void whenOfferFilter_byFeatureWithNullValues_shouldReturnSameCollectionWithoutNulls() {
    List<Feature> features = Arrays.asList(Feature.CD_PLAYER);
    List<Feature> featuresWithNulls = Arrays.asList(Feature.CD_PLAYER, null, null);

    Filter filter = Filter.builder().features(features).build();
    Filter filterFeaturesWithNulls = Filter.builder().features(featuresWithNulls).build();

    Specification<Offer> offerSpecification = where(OfferSpecifications.getOffersByFilter(filter));
    Specification<Offer> offerSpecificationFeaturesWithNulls =
        where(OfferSpecifications.getOffersByFilter(filterFeaturesWithNulls));

    List<Offer> offers = offerRepository.findAll(offerSpecification);
    List<Offer> offersFeaturesWithNulls =
        offerRepository.findAll(offerSpecificationFeaturesWithNulls);

    assertThat(offers).size().isEqualTo(offersFeaturesWithNulls.size());
  }
}
