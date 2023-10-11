package com.github.vaatech.aom.core.repository;

import com.github.vaatech.aom.core.model.vehicle.Maker;
import org.springframework.stereotype.Repository;

@Repository
public interface MakerRepository extends ApplicationJpaRepository<Maker, Integer>, MakerExtendedRepository {

    boolean existsByName(String name);
}
