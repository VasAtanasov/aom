package bg.autohouse.data.specifications;

import bg.autohouse.data.models.Filter;
import bg.autohouse.data.models.User_;
import bg.autohouse.data.models.account.Account_;
import bg.autohouse.data.models.enums.*;
import bg.autohouse.data.models.geo.Address_;
import bg.autohouse.data.models.offer.Offer;
import bg.autohouse.data.models.offer.Offer_;
import bg.autohouse.data.models.offer.Vehicle_;
import bg.autohouse.util.Assert;
import bg.autohouse.util.F;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

@Slf4j
public class OfferSpecifications {

  public static Specification<Offer> withOfferId(UUID offerId) {
    return (root, query, cb) -> cb.equal(root.get(Offer_.id), offerId);
  }

  public static Specification<Offer> withOwnerId(UUID userId) {
    return (root, query, cb) -> {
      performJoins(root, query);
      return cb.equal(root.get(Offer_.account).get(Account_.user).get(User_.id), userId);
    };
  }

  public static Specification<Offer> oneWithIdAndOwnerId(UUID offerId, UUID userId) {
    Specification<Offer> withId = OfferSpecifications.withOfferId(offerId);
    Specification<Offer> withOwner = OfferSpecifications.withOwnerId(userId);
    return Specification.where(withOwner).and(withId);
  }

  public static Specification<Offer> activeOffers() {
    return (root, query, cb) -> {
      return cb.equal(root.get(Offer_.isActive), true);
    };
  }

  public static Specification<Offer> activeUser() {
    return (root, query, cb) -> {
      return cb.equal(root.get(Offer_.account).get(Account_.user).get(User_.enabled), true);
    };
  }

  public static Specification<Offer> uuidIn(List<UUID> offersIds) {
    return (root, query, cb) -> {
      return cb.isTrue(root.get(Offer_.id).in(offersIds));
    };
  }

  public static Specification<Offer> favoriteWithIds(List<UUID> offersIds) {
    return (root, query, cb) -> {
      performJoins(root, query);
      query.distinct(true);
      return cb.isTrue(root.get(Offer_.id).in(offersIds));
    };
  }

  public static Specification<Offer> withAccountId(UUID accountId) {
    return (root, query, cb) -> {
      performJoins(root, query);
      query.distinct(true);
      return cb.equal(root.get(Offer_.account).get(Account_.id), accountId);
    };
  }

  public static Specification<Offer> getOffersByFilter(Filter filter) {
    return (root, query, cb) -> {
      List<Predicate> restrictions = new ArrayList<>();
      performJoins(root, query);

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

      if (!F.isNullOrEmpty(filter.getSeller())) {
        List<AccountType> sellers = F.filterToList(filter.getSeller(), Assert::has);
        restrictions.add(cb.isTrue(root.get(Offer_.account).get(Account_.accountType).in(sellers)));
      }

      if (!F.isNullOrEmpty(filter.getState())) {
        List<State> states = F.filterToList(filter.getState(), Assert::has);
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

  private static void performJoins(Root<Offer> root, CriteriaQuery<?> query) {
    if (currentQueryIsCountRecords(query)) {
      root.join(Offer_.vehicle, JoinType.INNER);
      root.join(Offer_.vehicle, JoinType.INNER).join(Vehicle_.features, JoinType.INNER);
      root.join(Offer_.location, JoinType.INNER);
      root.join(Offer_.account, JoinType.INNER).join(Account_.user, JoinType.INNER);
      root.join(Offer_.account, JoinType.INNER)
          .join(Account_.address, JoinType.INNER)
          .join(Address_.location, JoinType.INNER);

    } else {
      root.fetch(Offer_.vehicle, JoinType.INNER);
      root.fetch(Offer_.vehicle, JoinType.INNER).fetch(Vehicle_.features, JoinType.INNER);
      root.fetch(Offer_.location, JoinType.INNER);
      root.fetch(Offer_.account, JoinType.INNER);
      root.fetch(Offer_.account, JoinType.INNER).fetch(Account_.user, JoinType.INNER);
      root.fetch(Offer_.account, JoinType.INNER)
          .fetch(Account_.address, JoinType.INNER)
          .fetch(Address_.location, JoinType.INNER);
    }
  }
}
