package bg.autohouse.util;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class JsonParserImpl implements JsonParser {

  private final Gson gson;

  @Override
  public <O> O parseJson(Class<O> objectClass, String text) {
    return gson.fromJson(text, objectClass);
  }

  @Override
  public String toString(Object obj) {
    return gson.toJson(obj);
  }
}
