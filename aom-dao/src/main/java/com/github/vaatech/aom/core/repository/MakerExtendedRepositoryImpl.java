package com.github.vaatech.aom.core.repository;

import com.github.vaatech.aom.core.model.vehicle.Maker;
import jakarta.persistence.EntityManager;

public class MakerExtendedRepositoryImpl extends ApplicationJpaRepositoryImpl<Maker, Integer> implements MakerExtendedRepository {

    public MakerExtendedRepositoryImpl(EntityManager entityManager) {
        super(Maker.class, entityManager);
    }

}
