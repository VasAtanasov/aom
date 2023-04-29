package com.github.vaatech.aom.bl;

import com.github.vaatech.aom.api.dto.MakerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MakerService {
  Page<MakerDTO> fetchMakers(Pageable pageable);

  MakerDTO createMaker(MakerDTO maker);
}
