package bg.autohouse.data.specifications;

import bg.autohouse.data.models.Engine_;
import bg.autohouse.data.models.Filter;
import bg.autohouse.data.models.ManufactureDate_;
import bg.autohouse.data.models.Offer;
import bg.autohouse.data.models.Offer_;
import bg.autohouse.data.models.User_;
import bg.autohouse.data.models.Vehicle_;
import bg.autohouse.data.models.enums.*;
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
        root.join(Offer_.vehicle, JoinType.LEFT).join(Vehicle_.engine, JoinType.LEFT);
        root.join(Offer_.vehicle, JoinType.LEFT).join(Vehicle_.maker, JoinType.LEFT);
        root.join(Offer_.vehicle, JoinType.LEFT).join(Vehicle_.model, JoinType.LEFT);
        root.join(Offer_.vehicle, JoinType.LEFT).join(Vehicle_.feature, JoinType.LEFT);
        root.join(Offer_.user, JoinType.LEFT);
      } else {
        root.fetch(Offer_.vehicle, JoinType.LEFT).fetch(Vehicle_.engine, JoinType.LEFT);
        root.fetch(Offer_.vehicle, JoinType.LEFT).fetch(Vehicle_.maker, JoinType.LEFT);
        root.fetch(Offer_.vehicle, JoinType.LEFT).fetch(Vehicle_.model, JoinType.LEFT);
        root.fetch(Offer_.vehicle, JoinType.LEFT).fetch(Vehicle_.feature, JoinType.LEFT);
        root.fetch(Offer_.user, JoinType.LEFT);
      }

      restrictions.add(cb.equal(root.get(Offer_.isActive), Boolean.TRUE));
      restrictions.add(cb.equal(root.get(Offer_.isExpired), Boolean.FALSE));
      restrictions.add(cb.equal(root.get(Offer_.isDeleted), Boolean.FALSE));

      if (Assert.has(filter.getMaker())) {
        restrictions.add(cb.equal(root.get(Offer_.vehicle).get(Vehicle_.maker), filter.getMaker()));
      }

      if (Assert.has(filter.getModel())) {
        restrictions.add(cb.equal(root.get(Offer_.vehicle).get(Vehicle_.model), filter.getModel()));
      }

      if (Assert.has(filter.getFuelType())) {
        restrictions.add(
            cb.equal(
                root.get(Offer_.vehicle).get(Vehicle_.engine).get(Engine_.fuelType),
                filter.getFuelType()));
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

      if (Assert.has(filter.getEuroStandard())) {
        restrictions.add(
            cb.equal(
                root.get(Offer_.vehicle).get(Vehicle_.engine).get(Engine_.euroStandard),
                filter.getEuroStandard()));
      }

      if (Assert.has(filter.getDrive())) {
        restrictions.add(cb.equal(root.get(Offer_.vehicle).get(Vehicle_.drive), filter.getDrive()));
      }

      filter
          .getFeature()
          .forEach(
              feature -> {
                if (!Assert.has(feature)) return;
                restrictions.add(
                    cb.isMember(feature, root.get(Offer_.vehicle).get(Vehicle_.feature)));
              });

      if (!F.isNullOrEmpty(filter.getSeller())) {
        List<Seller> sellers = F.filterToList(filter.getSeller(), seller -> Assert.has(seller));
        restrictions.add(cb.isTrue(root.get(Offer_.user).get(User_.seller).in(sellers)));
      }

      if (!F.isNullOrEmpty(filter.getState())) {
        List<State> states = F.filterToList(filter.getState(), state -> Assert.has(state));
        restrictions.add(cb.isTrue(root.get(Offer_.vehicle).get(Vehicle_.state).in(states)));
      }

      if (!F.isNullOrEmpty(filter.getUpholstery())) {
        List<Upholstery> upholsteries =
            F.filterToList(filter.getUpholstery(), upholstery -> Assert.has(upholstery));
        restrictions.add(
            cb.isTrue(root.get(Offer_.vehicle).get(Vehicle_.upholstery).in(upholsteries)));
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
              root.get(Offer_.vehicle).get(Vehicle_.seats),
              filter.getSeats().getFrom(),
              filter.getSeats().getTo()));

      restrictions.add(
          cb.between(
              root.get(Offer_.vehicle).get(Vehicle_.manufactureDate).get(ManufactureDate_.year),
              filter.getRegistrationYear().getFrom(),
              filter.getRegistrationYear().getTo()));

      restrictions.add(
          cb.between(
              root.get(Offer_.vehicle).get(Vehicle_.engine).get(Engine_.power),
              filter.getHorsePower().getFrom(),
              filter.getHorsePower().getTo()));

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
}
