package com.github.vaatech.aom.api.dto.engine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnginesList implements Serializable {
  private List<EngineDTO> engines;
}
