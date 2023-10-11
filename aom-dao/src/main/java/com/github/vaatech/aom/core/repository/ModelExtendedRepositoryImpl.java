package com.github.vaatech.aom.core.repository;

import com.github.vaatech.aom.commons.basic.persistence.criteria.CriteriaObject;
import com.github.vaatech.aom.core.model.vehicle.Model;
import com.github.vaatech.aom.core.model.vehicle.Model_;
import com.github.vaatech.aom.core.specification.ModelSpecifications;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

public class ModelExtendedRepositoryImpl extends ApplicationJpaRepositoryImpl<Model, Integer> implements ModelExtendedRepository {

    public ModelExtendedRepositoryImpl(EntityManager entityManager) {
        super(Model.class, entityManager);
    }

    @Override
    public Page<Model> findAllByMakerName(String makerName, Pageable pageable) {
        Page<?> ids = Page.empty();
        if (pageable.isPaged()) ids = findIdsBy(ModelSpecifications.byMakerName(makerName), pageable);

        CriteriaObject<Model, Integer, Model> co = getCriteriaObject();

        co.cq().distinct(true);
        co.root().fetch(Model_.maker, JoinType.LEFT);

        Predicate predicate;
        if (pageable.isPaged()) {
            predicate = co.root().get(Model_.id).in(ids.getContent());
        } else {
            predicate = ModelSpecifications.byMakerName(makerName).toPredicate(co.root(), co.cq(), co.cb());
        }

        co.cq().where(predicate);
        addQueryOrders(pageable, co.cq(), co.cb(), co.root());

        TypedQuery<Model> query = getEntityManager().createQuery(co.cq());

        return PageableExecutionUtils.getPage(
                query.getResultList(),
                pageable,
                ids::getTotalElements);
    }
}
