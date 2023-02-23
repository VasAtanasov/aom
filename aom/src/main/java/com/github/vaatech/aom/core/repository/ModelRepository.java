package com.github.vaatech.aom.core.repository;

import com.github.vaatech.aom.core.model.vehicle.Model;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends ApplicationJpaRepository<Model, String> {

//  default Stream<Model> findAllCarsByModelId(String modelId) {
//    return findOne(ModelSpecifications.carsForModelId(modelId)).stream();
//  }
}
