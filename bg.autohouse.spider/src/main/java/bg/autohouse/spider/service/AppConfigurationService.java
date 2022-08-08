package bg.autohouse.spider.service;

import bg.autohouse.spider.constants.Constant;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;

public class AppConfigurationService implements Configuration {

  private static final Properties properties = new Properties();

  private AppConfigurationService() {
    init();
  }

  public enum ConfigKeys {
    FILE_STORAGE_FOLDER("file.storage.folder");

    private final String key;

    ConfigKeys(String key) {
      this.key = key;
    }

    public String getKey() {
      return key;
    }

    public String getPlaceholder() {
      return name();
    }
  }

  public static AppConfigurationService create() {
    return new AppConfigurationService();
  }

  @Override
  public String getProperty(String key) {
    return properties.getProperty(key);
  }

  @Override
  public void addProperty(String key, Object prop) {
    properties.put(key, prop);
  }

  @Override
  public void removeProperty(String key) {
    properties.remove(key);
  }

  public static String storageBasePath() {
    return properties.getProperty(
        ConfigKeys.FILE_STORAGE_FOLDER.getKey(), Constant.TEMP_STORAGE_PATH);
  }

  private void init() {
    try {
      Properties defaultInstProps = new Properties();
      defaultInstProps.load(
          AppConfigurationService.class
              .getClassLoader()
              .getResourceAsStream("application.properties"));

      for (Entry<Object, Object> entry : defaultInstProps.entrySet()) {
        String key = (String) entry.getKey();
        String value = resolvePlaceholder((String) entry.getValue());
        if (value == null) continue;
        properties.put(key, value);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String resolvePlaceholder(String value) {
    if (value.startsWith("${") && value.endsWith("}")) {
      String placeholder = value.substring(2, value.length() - 1);
      return System.getenv(placeholder);
    }
    return value;
  }
}
