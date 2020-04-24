package bg.autohouse.data.repositories;

import bg.autohouse.data.models.PersistentFileContent;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersistentFileContentRepository
    extends JpaRepository<PersistentFileContent, UUID> {}