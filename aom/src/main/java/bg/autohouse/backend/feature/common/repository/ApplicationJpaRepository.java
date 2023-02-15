package bg.autohouse.backend.feature.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import jakarta.persistence.EntityManager;
import java.io.Serializable;

@NoRepositoryBean
public interface ApplicationJpaRepository<T, ID extends Serializable>
    extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
  EntityManager getEntityManager();
}
