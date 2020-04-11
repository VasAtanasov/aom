package bg.autohouse.data.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.jpa.domain.Specification.where;

import bg.autohouse.data.models.Filter;
import bg.autohouse.data.models.Maker;
import bg.autohouse.data.models.Offer;
import bg.autohouse.data.models.enums.BodyStyle;
import bg.autohouse.data.models.enums.Feature;
import bg.autohouse.data.models.enums.FuelType;
import bg.autohouse.data.models.enums.SellerType;
import bg.autohouse.data.models.enums.State;
import bg.autohouse.data.specifications.OfferSpecifications;
import java.util.Arrays;
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
  // @Sql("/data.sql")
  void whenFindLatestOffers_shouldReturnCollection() {

    Stream<Offer> offers = offerRepository.findLatestOffers(DEFAULT_PAGEABLE);
    Page<Offer> offersPage = offerRepository.findLatestOffersPage(DEFAULT_PAGEABLE);

    assertThat(offers).size().isEqualTo(DEFAULT_SIZE);
    assertThat(offersPage.getTotalElements()).isEqualTo(100);
  }

  @Test
  void whenOfferFilter_byFuelType_shouldReturnCollection() {
    Filter filter = Filter.builder().fuelType(FuelType.GASOLINE).build();

    Specification<Offer> offerSpecification = where(OfferSpecifications.getOffersByFilter(filter));

    List<Offer> offers = offerRepository.findAll(offerSpecification);

    assertThat(offers)
        .allMatch(offer -> offer.getVehicle().getEngine().getFuelType().equals(FuelType.GASOLINE));
  }

  @Test
  void whenOfferFilter_byBodyStyle_shouldReturnCollection() {
    Filter filter = Filter.builder().bodyStyle(BodyStyle.SUV).build();

    Specification<Offer> offerSpecification = where(OfferSpecifications.getOffersByFilter(filter));

    List<Offer> offers = offerRepository.findAll(offerSpecification);

    assertThat(offers).allMatch(offer -> offer.getVehicle().getBodyStyle().equals(BodyStyle.SUV));
  }

  @Test
  void whenOfferFilter_byMaker_shouldReturnCollection() {
    Maker maker = Maker.builder().id(23L).name("Cupra").build();
    Filter filter = Filter.builder().maker(maker).build();

    Specification<Offer> offerSpecification = where(OfferSpecifications.getOffersByFilter(filter));

    List<Offer> offers = offerRepository.findAll(offerSpecification);

    assertThat(offers).allMatch(offer -> offer.getVehicle().getMaker().equals(maker));
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
  void whenFilterOffer_pageable_shouldReturnCollection() {
    List<State> state = Arrays.asList(State.NEW, State.USED);
    Filter filter = Filter.builder().state(state).build();
    Specification<Offer> offerSpecification = where(OfferSpecifications.getOffersByFilter(filter));

    Sort sort = Sort.by("createdAt", "hitCount").descending();
    Pageable pageable = PageRequest.of(0, 20, sort);

    Page<Offer> offersPage = offerRepository.findAll(offerSpecification, pageable);

    assertThat(offersPage.getContent()).isNotEmpty();
  }

  @Test
  void whenOfferFilter_bySeller_shouldReturnCollection() {
    List<SellerType> sellerTypes = Arrays.asList(SellerType.DEALER);
    Filter filter = Filter.builder().sellerTypes(sellerTypes).build();

    Specification<Offer> offerSpecification = where(OfferSpecifications.getOffersByFilter(filter));

    List<Offer> offers = offerRepository.findAll(offerSpecification);

    assertThat(offers).allMatch(offer -> sellerTypes.contains(offer.getSeller().getSellerType()));
  }

  @Test
  void whenOfferFilter_byFeatureWithNullValues_shouldReturnSameCollectionWithoutNulls() {
    List<Feature> features = Arrays.asList(Feature.CD_PLAYER);
    List<Feature> featuresWithNulls = Arrays.asList(Feature.CD_PLAYER, null, null);

    Filter filter = Filter.builder().feature(features).build();
    Filter filterFeaturesWithNulls = Filter.builder().feature(featuresWithNulls).build();

    Specification<Offer> offerSpecification = where(OfferSpecifications.getOffersByFilter(filter));
    Specification<Offer> offerSpecificationFeaturesWithNulls =
        where(OfferSpecifications.getOffersByFilter(filterFeaturesWithNulls));

    List<Offer> offers = offerRepository.findAll(offerSpecification);
    List<Offer> offersFeaturesWithNulls =
        offerRepository.findAll(offerSpecificationFeaturesWithNulls);

    assertThat(offers).size().isEqualTo(offersFeaturesWithNulls.size());
  }
}
