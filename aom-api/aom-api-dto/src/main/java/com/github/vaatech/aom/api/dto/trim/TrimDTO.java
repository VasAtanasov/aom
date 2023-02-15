package com.github.vaatech.aom.api.dto.trim;

import com.github.vaatech.aom.api.dto.BaseDTO;
import lombok.Data;

@Data
public class TrimDTO implements BaseDTO<String> {
  private String id;
  private String name;
}
