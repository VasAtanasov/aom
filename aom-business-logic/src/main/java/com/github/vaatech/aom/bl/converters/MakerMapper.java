package com.github.vaatech.aom.bl.converters;

import com.github.vaatech.aom.api.dto.MakerDTO;
import com.github.vaatech.aom.core.model.vehicle.Maker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MakerMapper {
  MakerMapper INSTANCE = Mappers.getMapper(MakerMapper.class);

  Maker toEntity(MakerDTO dto);

  MakerDTO toDto(Maker entity);

  void updateEntity(MakerDTO dto, @MappingTarget Maker entity);
}
