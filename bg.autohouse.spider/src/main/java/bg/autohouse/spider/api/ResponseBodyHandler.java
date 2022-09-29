package bg.autohouse.spider.api;

import bg.autohouse.spider.util.IOUtil;
import bg.autohouse.spider.util.json.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.InputStream;

@FunctionalInterface
public interface ResponseBodyHandler<T> {
  T handle(InputStream inputStream);

  class BodyHandlers {
    public static <T> ResponseBodyHandler<T> ofJson(Class<T> clazz) {
      return in -> JsonUtil.fromJSON(in, clazz);
    }

    public static <T> ResponseBodyHandler<T> ofJson(TypeReference<T> type) {
      return in -> JsonUtil.fromJSON(in, type);
    }

    public static ResponseBodyHandler<String> ofString() {
      return IOUtil::toString;
    }
  }
}
