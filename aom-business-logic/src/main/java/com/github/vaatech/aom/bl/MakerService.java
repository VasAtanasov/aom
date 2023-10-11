package com.github.vaatech.aom.bl;

import com.github.vaatech.aom.api.dto.MakerDTO;
import com.github.vaatech.aom.core.model.vehicle.Maker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MakerService extends CRUDService<Integer, Maker, MakerDTO> {
    Page<MakerDTO> fetchMakers(Pageable pageable);
}
