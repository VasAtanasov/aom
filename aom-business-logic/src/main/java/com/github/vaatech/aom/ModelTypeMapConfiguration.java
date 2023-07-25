package com.github.vaatech.aom;

import com.github.vaatech.aom.api.dto.ModelDTO;
import com.github.vaatech.aom.commons.utils.modelmapper.TypeMapConfigurerSupport;
import com.github.vaatech.aom.core.model.vehicle.Model;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelTypeMapConfiguration {

  @Configuration
  public static class ModelToModelDTOTypeMapConfigurer
      extends TypeMapConfigurerSupport<Model, ModelDTO> {
    @Override
    public void typeMap(TypeMap<Model, ModelDTO> typeMap) {
      typeMap.addMappings(
          mapper -> {
            mapper.map(src -> src.getMaker().getName(), ModelDTO::setMakerName);
            mapper.map(src -> src.getMaker().getId(), ModelDTO::setMakerId);
          });
    }
  }
}
