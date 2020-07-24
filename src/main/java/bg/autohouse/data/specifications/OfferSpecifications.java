package bg.autohouse.data.specifications;

import bg.autohouse.data.models.Filter;
import bg.autohouse.data.models.account.Account_;
import bg.autohouse.data.models.enums.*;
import bg.autohouse.data.models.offer.Offer;
import bg.autohouse.data.models.offer.Offer_;
import bg.autohouse.data.models.offer.Vehicle_;
import bg.autohouse.util.Assert;
import bg.autohouse.util.F;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

@Slf4j
public class OfferSpecifications {

  public static Specification<Offer> getOffersByFilter(Filter filter) {
    return (root, query, cb) -> {
      List<Predicate> restrictions = new ArrayList<>();

      if (currentQueryIsCountRecords(query)) {
        root.join(Offer_.vehicle, JoinType.INNER);
        root.join(Offer_.vehicle, JoinType.INNER).join(Vehicle_.features, JoinType.INNER);
        root.join(Offer_.location, JoinType.INNER);
      } else {
        root.fetch(Offer_.vehicle, JoinType.INNER);
        root.fetch(Offer_.vehicle, JoinType.INNER).fetch(Vehicle_.features, JoinType.INNER);
        root.fetch(Offer_.location, JoinType.INNER);
      }

      restrictions.add(cb.equal(root.get(Offer_.isActive), Boolean.TRUE));

      if (Assert.has(filter.getMakerName())) {
        restrictions.add(
            cb.equal(root.get(Offer_.vehicle).get(Vehicle_.makerName), filter.getMakerName()));
      }

      if (Assert.has(filter.getModelName())) {
        restrictions.add(
            cb.equal(root.get(Offer_.vehicle).get(Vehicle_.modelName), filter.getModelName()));
      }

      if (Assert.has(filter.getTrim())) {
        restrictions.add(cb.equal(root.get(Offer_.vehicle).get(Vehicle_.trim), filter.getTrim()));
      }

      if (Assert.has(filter.getFuelType())) {
        restrictions.add(
            cb.equal(root.get(Offer_.vehicle).get(Vehicle_.fuelType), filter.getFuelType()));
      }

      if (Assert.has(filter.getTransmission())) {
        restrictions.add(
            cb.equal(
                root.get(Offer_.vehicle).get(Vehicle_.transmission), filter.getTransmission()));
      }

      if (Assert.has(filter.getBodyStyle())) {
        restrictions.add(
            cb.equal(root.get(Offer_.vehicle).get(Vehicle_.bodyStyle), filter.getBodyStyle()));
      }

      if (Assert.has(filter.getColor())) {
        restrictions.add(cb.equal(root.get(Offer_.vehicle).get(Vehicle_.color), filter.getColor()));
      }

      if (Assert.has(filter.getDrive())) {
        restrictions.add(cb.equal(root.get(Offer_.vehicle).get(Vehicle_.drive), filter.getDrive()));
      }

      if (!F.isNullOrEmpty(filter.getFeatures())) {
        List<Predicate> featuresPredicate = new ArrayList<>();
        Expression<List<Feature>> offerFeatures = root.get(Offer_.vehicle).get(Vehicle_.features);
        F.filterToList(filter.getFeatures(), feature -> Assert.has(feature))
            .forEach(feature -> featuresPredicate.add(cb.isMember(feature, offerFeatures)));
        restrictions.add(cb.and(featuresPredicate.toArray(new Predicate[0])));
      }

      if (!F.isNullOrEmpty(filter.getSeller())) {
        List<AccountType> sellers =
            F.filterToList(filter.getSeller(), seller -> Assert.has(seller));
        restrictions.add(cb.isTrue(root.get(Offer_.account).get(Account_.accountType).in(sellers)));
      }

      if (!F.isNullOrEmpty(filter.getState())) {
        List<State> states = F.filterToList(filter.getState(), state -> Assert.has(state));
        restrictions.add(cb.isTrue(root.get(Offer_.vehicle).get(Vehicle_.state).in(states)));
      }

      if (Assert.has(filter.getHasAccident())) {
        restrictions.add(
            cb.equal(root.get(Offer_.vehicle).get(Vehicle_.hasAccident), filter.getHasAccident()));
      }

      restrictions.add(
          cb.between(
              root.get(Offer_.price), filter.getPrice().getFrom(), filter.getPrice().getTo()));

      restrictions.add(
          cb.between(
              root.get(Offer_.vehicle).get(Vehicle_.mileage),
              filter.getMileage().getFrom(),
              filter.getMileage().getTo()));

      restrictions.add(
          cb.between(
              root.get(Offer_.vehicle).get(Vehicle_.doors),
              filter.getDoors().getFrom(),
              filter.getDoors().getTo()));

      restrictions.add(
          cb.between(
              root.get(Offer_.vehicle).get(Vehicle_.year),
              filter.getYear().getFrom(),
              filter.getYear().getTo()));

      restrictions.add(cb.equal(root.get(Offer_.isActive), true));

      log.info(
          "Have generated {} predicates, look like: {}" + System.lineSeparator(),
          restrictions.size(),
          restrictions.stream()
              .map(Predicate::toString)
              .collect(Collectors.joining(System.lineSeparator())));

      query.distinct(true);

      return cb.and(restrictions.toArray(new Predicate[0]));
    };
  }

  private static boolean currentQueryIsCountRecords(CriteriaQuery<?> criteriaQuery) {
    return criteriaQuery.getResultType() == Long.class
        || criteriaQuery.getResultType() == long.class;
  }

  public static Specification<Offer> withFeature(Feature feature) {
    return (root, query, cb) ->
        cb.isMember(feature, root.get(Offer_.vehicle).get(Vehicle_.features));
  }
}
