package bg.autohouse.backend.feature.common.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class ApplicationRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements ApplicationRepository<T, ID> {

    private final EntityManager entityManager;

    public ApplicationRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }
}