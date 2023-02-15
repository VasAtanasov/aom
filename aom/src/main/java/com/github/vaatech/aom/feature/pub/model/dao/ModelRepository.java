package com.github.vaatech.aom.feature.pub.model.dao;

import com.github.vaatech.aom.feature.common.repository.ApplicationJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends ApplicationJpaRepository<Model, String> {

//  default Stream<Model> findAllCarsByModelId(String modelId) {
//    return findOne(ModelSpecifications.carsForModelId(modelId)).stream();
//  }
}
