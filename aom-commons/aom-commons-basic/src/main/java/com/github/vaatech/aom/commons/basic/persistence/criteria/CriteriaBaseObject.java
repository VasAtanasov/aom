package com.github.vaatech.aom.commons.basic.persistence.criteria;

import com.github.vaatech.aom.commons.basic.persistence.model.BaseEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;

import java.io.Serializable;

public class CriteriaBaseObject<E extends BaseEntity<ID>, ID extends Serializable> {
    private final CriteriaBuilder cb;
    private final Root<E> root;

    public CriteriaBaseObject(final CriteriaBuilder cb, final Root<E> root) {
        this.cb = cb;
        this.root = root;
    }

    public CriteriaBuilder cb() {
        return cb;
    }

    public Root<E> root() {
        return root;
    }
}
