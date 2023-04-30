package com.github.vaatech.aom.bl.impl;

import com.github.vaatech.aom.api.dto.MakerDTO;
import com.github.vaatech.aom.bl.MakerService;
import com.github.vaatech.aom.bl.converters.MakerMapper;
import com.github.vaatech.aom.core.model.vehicle.Maker;
import com.github.vaatech.aom.core.repository.MakerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MakerServiceImpl implements MakerService {
  private final MakerRepository makerRepository;

  public MakerServiceImpl(MakerRepository makerRepository) {
    this.makerRepository = makerRepository;
  }

  @Override
  public Page<MakerDTO> fetchMakers(Pageable pageable) {
    return makerRepository.findAll(pageable).map(MakerMapper.INSTANCE::toDto);
  }

  @Override
  public MakerDTO createMaker(MakerDTO maker) {
    Maker makerEntity = MakerMapper.INSTANCE.toEntity(maker);
    Maker savedMaker = makerRepository.save(makerEntity);
    return MakerMapper.INSTANCE.toDto(savedMaker);
  }
}
