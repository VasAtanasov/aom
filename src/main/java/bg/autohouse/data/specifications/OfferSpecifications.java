package bg.autohouse.data.specifications;

import bg.autohouse.data.models.Filter;
import bg.autohouse.data.models.Offer;
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

      // if (Assert.has(filter.getFuelType())) {
      //   restrictions.add(cb.equal(root.get(Offer_.vehicle), y));
      // }

      log.info(
          "Have generated {} predicates, look like: {}",
          restrictions.size(),
          restrictions.stream().map(Predicate::toString).collect(Collectors.joining(", ")));
      return cb.and(restrictions.toArray(new Predicate[0]));
    };
  }
}
