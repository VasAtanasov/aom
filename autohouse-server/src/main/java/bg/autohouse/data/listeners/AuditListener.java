package bg.autohouse.data.listeners;

import bg.autohouse.data.models.Audit;
import bg.autohouse.data.models.Auditable;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class AuditListener {

    @PrePersist
    public void setCreatedOn(Auditable auditable) {
        Audit audit = auditable.getAudit();

        if (audit == null) {
            audit = new Audit();
            auditable.setAudit(audit);
        }

        audit.setCreatedOn(LocalDateTime.now());
    }

    @PreUpdate
    public void setModifiedOn(Auditable auditable) {
        Audit audit = auditable.getAudit();

        audit.setModifiedOn(LocalDateTime.now());
    }
}