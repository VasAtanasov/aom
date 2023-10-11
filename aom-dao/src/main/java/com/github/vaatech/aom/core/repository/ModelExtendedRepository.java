package com.github.vaatech.aom.core.repository;

import com.github.vaatech.aom.core.model.vehicle.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ModelExtendedRepository {

    Page<Model> findAllByMakerName(String makerName, Pageable pageable);
}
