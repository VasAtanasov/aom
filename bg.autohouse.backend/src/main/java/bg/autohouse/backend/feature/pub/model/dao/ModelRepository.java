package bg.autohouse.backend.feature.pub.model.dao;

import bg.autohouse.backend.feature.common.repository.ApplicationJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface ModelRepository extends ApplicationJpaRepository<Model, String> {

//  default Stream<Model> findAllCarsByModelId(String modelId) {
//    return findOne(ModelSpecifications.carsForModelId(modelId)).stream();
//  }
}
