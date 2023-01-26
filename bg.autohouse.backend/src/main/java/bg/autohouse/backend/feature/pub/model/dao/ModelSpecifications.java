package bg.autohouse.backend.feature.pub.model.dao;

import bg.autohouse.backend.feature.pub.car.dao.Car;
import bg.autohouse.backend.feature.pub.car.dao.Car_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class ModelSpecifications {

  public static Specification<Model> carsForModelId(String id) {
    return (root, query, cb) -> {
      Join<Model, Car> join = root.join(Model_.cars, JoinType.LEFT);
      query.distinct(true);
      return cb.equal(join.get(Car_.model), id);
    };
  }

  public static Specification<Model> byId(String id) {
    return (root, query, cb) -> cb.equal(root.get(Model_.id), id);
  }
}
