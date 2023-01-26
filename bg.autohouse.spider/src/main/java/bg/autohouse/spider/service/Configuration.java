package bg.autohouse.spider.service;

import java.io.Serializable;

public interface Configuration extends Serializable {
  String getProperty(String key);

  void addProperty(String key, Object prop);

  void removeProperty(String key);
}
