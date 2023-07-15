package com.github.vaatech.aom.bl;

import com.github.vaatech.aom.commons.basic.dto.BaseDTO;
import com.github.vaatech.aom.commons.basic.persistence.model.BaseEntity;
import java.io.Serializable;

public interface CRUDService<
    ID extends Serializable, E extends BaseEntity<ID>, D extends BaseDTO<ID>> {
  D read(ID id);

  D create(D dto);

  D update(D dto);

  void delete(ID id);
}
