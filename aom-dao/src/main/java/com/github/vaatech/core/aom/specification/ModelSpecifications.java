package com.github.vaatech.core.aom.specification;

import com.github.vaatech.aom.core.model.vehicle.Model;
import com.github.vaatech.aom.core.model.vehicle.ModelYear;
import com.github.vaatech.aom.core.model.vehicle.ModelYear_;
import com.github.vaatech.aom.core.model.vehicle.Model_;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class ModelSpecifications {

  public static Specification<Model> carsForModelId(String id) {
    return (root, query, cb) -> {
      Join<Model, ModelYear> join = root.join(Model_.modelYears, JoinType.LEFT);
      query.distinct(true);
      return cb.equal(join.get(ModelYear_.model), id);
    };
  }

  public static Specification<Model> byId(String id) {
    return (root, query, cb) -> cb.equal(root.get(Model_.id), id);
  }
}
