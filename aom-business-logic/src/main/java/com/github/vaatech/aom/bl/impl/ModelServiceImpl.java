package com.github.vaatech.aom.bl.impl;

import com.github.vaatech.aom.api.dto.ModelDTO;
import com.github.vaatech.aom.bl.AbstractCRUDService;
import com.github.vaatech.aom.bl.ModelService;
import com.github.vaatech.aom.commons.utils.modelmapper.MappingUtil;
import com.github.vaatech.aom.core.model.vehicle.Model;
import com.github.vaatech.aom.core.repository.ApplicationJpaRepository;
import com.github.vaatech.aom.core.repository.ModelRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModelServiceImpl extends AbstractCRUDService<Integer, Model, ModelDTO>
        implements ModelService {

    private final ModelRepository modelRepository;

    public ModelServiceImpl(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    protected ApplicationJpaRepository<Model, Integer> getRepository() {
        return modelRepository;
    }

    @Override
    protected void updateEntity(Model entity, ModelDTO dto) {
        MappingUtil.map(dto, entity);
    }

    @Override
    protected ModelDTO toDTO(Model entity) {
        return MappingUtil.map(entity, ModelDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ModelDTO> fetchMakerModels(String makerName, Pageable pageable) {
        return modelRepository
                .findAllByMakerName(makerName, pageable)
                .map(maker -> MappingUtil.map(maker, ModelDTO.class));
    }
}
