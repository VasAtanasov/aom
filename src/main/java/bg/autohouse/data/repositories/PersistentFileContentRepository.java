package bg.autohouse.data.repositories;

import bg.autohouse.data.models.PersistentFileContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersistentFileContentRepository
    extends JpaRepository<PersistentFileContent, UUID> {}
