package com.github.vaatech.aom.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionsWrapper implements Serializable {
  private Map<String, List<OptionDTO>> optionsMap;
}
