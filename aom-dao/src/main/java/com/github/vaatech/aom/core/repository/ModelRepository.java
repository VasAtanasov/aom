package com.github.vaatech.aom.core.repository;

import com.github.vaatech.aom.core.model.vehicle.Model;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends ApplicationJpaRepository<Model, Integer>, ModelExtendedRepository {

}
