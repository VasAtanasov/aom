package com.github.vaatech.aom.commons.basic.persistence.criteria;

import com.github.vaatech.aom.commons.basic.persistence.model.BaseEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.io.Serializable;

public class CriteriaObject<E extends BaseEntity<ID>, ID extends Serializable, R>
    extends CriteriaBaseObject<E, ID> {
  private final CriteriaQuery<R> cq;

  public CriteriaObject(final CriteriaBuilder cb, final CriteriaQuery<R> cq, final Root<E> root) {
    super(cb, root);
    this.cq = cq;
  }

  public CriteriaQuery<R> cq() {
    return cq;
  }
}
