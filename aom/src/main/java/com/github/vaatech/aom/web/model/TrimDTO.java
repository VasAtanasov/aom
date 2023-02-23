package com.github.vaatech.aom.web.model;

import com.github.vaatech.aom.core.model.common.BaseDTO;
import lombok.Data;

@Data
public class TrimDTO implements BaseDTO<String> {
  private String id;
  private String name;
}
