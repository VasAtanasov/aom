package com.github.vaatech.aom.bl.impl;

import com.github.vaatech.aom.api.dto.MakerDTO;
import com.github.vaatech.aom.bl.AbstractCRUDService;
import com.github.vaatech.aom.bl.MakerService;
import com.github.vaatech.aom.bl.converters.MakerMapper;
import com.github.vaatech.aom.core.model.vehicle.Maker;
import com.github.vaatech.aom.core.repository.ApplicationJpaRepository;
import com.github.vaatech.aom.core.repository.MakerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MakerServiceImpl extends AbstractCRUDService<Integer, Maker, MakerDTO>
    implements MakerService {
  private final MakerRepository makerRepository;

  public MakerServiceImpl(MakerRepository makerRepository) {
    this.makerRepository = makerRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<MakerDTO> fetchMakers(Pageable pageable) {
    return makerRepository.findAll(pageable).map(MakerMapper.INSTANCE::toDto);
  }

  @Override
  protected ApplicationJpaRepository<Maker, Integer> getRepository() {
    return makerRepository;
  }

  @Override
  protected void updateEntity(Maker entity, MakerDTO dto) {
    MakerMapper.INSTANCE.updateEntity(dto, entity);
  }

  @Override
  protected MakerDTO toDTO(Maker entity) {
    return MakerMapper.INSTANCE.toDto(entity);
  }
}
