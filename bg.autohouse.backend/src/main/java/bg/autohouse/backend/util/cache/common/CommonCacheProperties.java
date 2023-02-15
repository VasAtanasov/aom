package bg.autohouse.backend.util.cache.common;

import java.io.Serializable;

public abstract class CommonCacheProperties<T extends Serializable> {
  private String tenantId;
  private String cacheName;
  private long expireMills = 60_000;
  private Class<T> resultType;

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public String getCacheName() {
    return cacheName;
  }

  public void setCacheName(String cacheName) {
    this.cacheName = cacheName;
  }

  public long getExpireMills() {
    return expireMills;
  }

  public void setExpireMills(long expireMills) {
    this.expireMills = expireMills;
  }

  public Class<T> getResultType() {
    return resultType;
  }

  public void setResultType(Class<T> resultType) {
    this.resultType = resultType;
  }
}
