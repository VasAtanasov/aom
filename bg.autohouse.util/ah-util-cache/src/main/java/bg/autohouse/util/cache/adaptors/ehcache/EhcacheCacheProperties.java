package bg.autohouse.util.cache.adaptors.ehcache;

import bg.autohouse.util.cache.common.CommonCacheProperties;

import java.io.Serializable;
import java.nio.file.Path;

public class EhcacheCacheProperties<T extends Serializable> extends CommonCacheProperties<T> {
  private ResourceType type = ResourceType.HEAP;
  private long size;
  private boolean persistable;
  private Path storagePath;

  public Path getStoragePath() {
    return storagePath;
  }

  public void setStoragePath(Path storagePath) {
    this.storagePath = storagePath;
  }

  public boolean isPersistable() {
    return persistable;
  }

  public void setPersistable(boolean persistable) {
    this.persistable = persistable;
  }

  public ResourceType getType() {
    return type;
  }

  public void setType(ResourceType type) {
    this.type = type;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }
}
