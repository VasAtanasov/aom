package bg.autohouse.data.repositories.impl;

import bg.autohouse.data.models.Filter;
import bg.autohouse.data.repositories.FilterRepositoryCustom;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class FilterRepositoryImpl implements FilterRepositoryCustom {

  @PersistenceContext private EntityManager entityManager;

  @Override
  @Transactional(readOnly = true)
  public List<Filter> findAllByUserId(UUID userId) {
    List<Filter> filters =
        entityManager
            .createQuery(
                "SELECT DISTINCT f "
                    + "FROM Filter f "
                    + "LEFT JOIN FETCH f.features ft "
                    + "WHERE f.user.id = :userId "
                    + "ORDER BY f.createdAt ASC",
                Filter.class)
            .setParameter("userId", userId)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();

    filters =
        entityManager
            .createQuery(
                "SELECT DISTINCT f "
                    + "FROM Filter f "
                    + "LEFT JOIN FETCH f.seller s "
                    + "WHERE f IN :filters "
                    + "ORDER BY f.createdAt ASC",
                Filter.class)
            .setParameter("filters", filters)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();

    filters =
        entityManager
            .createQuery(
                "SELECT DISTINCT f "
                    + "FROM Filter f "
                    + "LEFT JOIN FETCH f.state st "
                    + "WHERE f IN :filters "
                    + "ORDER BY f.createdAt ASC",
                Filter.class)
            .setParameter("filters", filters)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    return filters;
  }
}
