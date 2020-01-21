package bg.autohouse.data.models;

public interface Auditable {

    Audit getAudit();

    void setAudit(Audit audit);
}
