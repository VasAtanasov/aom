package bg.autohouse.data.specifications;

import bg.autohouse.data.models.Engine_;
import bg.autohouse.data.models.Filter;
import bg.autohouse.data.models.ManufactureDate_;
import bg.autohouse.data.models.Offer;
import bg.autohouse.data.models.Offer_;
import bg.autohouse.data.models.Vehicle_;
import bg.autohouse.data.models.enums.FuelType;
import bg.autohouse.util.Assert;
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

      if (Assert.has(filter.getMaker())) {
        restrictions.add(cb.equal(root.get(Offer_.vehicle).get(Vehicle_.maker), filter.getMaker()));
      }

      if (Assert.has(filter.getMaker())) {
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

      log.info(
          "Have generated {} predicates, look like: {}",
          restrictions.size(),
          restrictions.stream().map(Predicate::toString).collect(Collectors.joining(", ")));
      return cb.and(restrictions.toArray(new Predicate[0]));
    };
  }

  public static Specification<Offer> hasFuelType(FuelType fuelType) {
    return (root, query, cb) ->
        cb.equal(root.get(Offer_.vehicle).get(Vehicle_.engine).get(Engine_.fuelType), fuelType);
  }
}
