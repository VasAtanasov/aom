package bg.autohouse.spider.service;

import bg.autohouse.spider.constants.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Properties;

public class AppConfigurationService implements Configuration {

  private static final Logger log = LoggerFactory.getLogger(AppConfigurationService.class);

  private static final Properties properties = new Properties();
  private static final String START_ENV_PLACEHOLDER_STR = "${";
  private static final String END_ENV_PLACEHOLDER_STR = "}";

  private AppConfigurationService() {
    init();
  }

  public static AppConfigurationService create() {
    return new AppConfigurationService();
  }

  public static String storageBasePath() {
    return properties.getProperty(
        ConfigKeys.FILE_STORAGE_FOLDER.getKey(), Constant.TEMP_STORAGE_PATH);
  }

  public static boolean isStubbingEnabled() {
    return Boolean.parseBoolean(
        properties.getProperty(ConfigKeys.STUBBING_ENABLED.getKey(), "false"));
  }

  public static String getStubbingServerUrl() {
    return properties.getProperty(
        ConfigKeys.STUBBING_SERVER_URL.getKey(), "http://localhost:18008");
  }

  public static String getCGApiBaseUrl() {
    if (isStubbingEnabled()) {
      return getStubbingServerUrl();
    }
    return properties.getProperty(ConfigKeys.CG_API_BASE_URL.getKey(), "http://localhost:18080");
  }

  public static String getALApiBaseUrl() {
    if (isStubbingEnabled()) {
      return getStubbingServerUrl();
    }
    return properties.getProperty(ConfigKeys.AL_API_BASE_URL.getKey(), "http://localhost:18080");
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
        log.info("Config: " + key + "=" + value);
        properties.put(key, value);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String resolvePlaceholder(String value) {
    Objects.requireNonNull(value);
    int starPlaceholder = value.indexOf(START_ENV_PLACEHOLDER_STR);
    int endPlaceholder = value.indexOf(END_ENV_PLACEHOLDER_STR);
    if (starPlaceholder != -1 && endPlaceholder != -1) {
      String placeholder =
          value.substring(starPlaceholder, endPlaceholder + END_ENV_PLACEHOLDER_STR.length());
      String placeholderStripped =
          value.substring(starPlaceholder + START_ENV_PLACEHOLDER_STR.length(), endPlaceholder);
      String env = System.getenv(placeholderStripped);

      if (env == null) return value;

      return resolvePlaceholder(value.replace(placeholder, env));
    }
    return value;
  }

  public enum ConfigKeys {
    FILE_STORAGE_FOLDER("file.storage.folder"),
    STUBBING_ENABLED("stubbing.enabled"),
    STUBBING_SERVER_URL("stubbing.server.url"),
    CG_API_BASE_URL("api.client.cg.base.url"),
    AL_API_BASE_URL("api.client.al.base.url"),
    ;

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
}
