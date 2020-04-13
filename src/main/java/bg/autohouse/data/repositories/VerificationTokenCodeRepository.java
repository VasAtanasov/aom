package bg.autohouse.data.repositories;

import bg.autohouse.data.models.VerificationTokenCode;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface VerificationTokenCodeRepository
    extends CrudRepository<VerificationTokenCode, Long>,
        JpaSpecificationExecutor<VerificationTokenCode> {

  VerificationTokenCode findByUsernameAndCode(String username, String code);

  VerificationTokenCode findByUsername(String username);
}
