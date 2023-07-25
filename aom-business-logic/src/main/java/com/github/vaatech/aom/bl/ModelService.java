package com.github.vaatech.aom.bl;

import com.github.vaatech.aom.api.dto.ModelDTO;
import com.github.vaatech.aom.core.model.vehicle.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ModelService extends CRUDService<Integer, Model, ModelDTO> {
  Page<ModelDTO> fetchMakerModels(String makerName, Pageable pageable);
}
