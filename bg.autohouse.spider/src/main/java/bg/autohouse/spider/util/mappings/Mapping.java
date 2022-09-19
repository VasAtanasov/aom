package bg.autohouse.spider.util.mappings;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Data
public class Mapping {
  private String id;
  private String uuid;
  private Request request;
  private Response response;

  public static Mapping from(Mapping target) {
    Mapping mapping = new Mapping();
    mapping.id = target.id;
    mapping.uuid = target.uuid;
    mapping.request = Request.from(target.request);

    Response response = Response.from(target.response);
    Map<String, Object> headers =
        response.getHeaders().entrySet().stream()
            .filter(entry -> entry.getKey().equalsIgnoreCase("Content-Type"))
            .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> a, HashMap::new));

    response.setHeaders(headers);
    mapping.response = response;

    return mapping;
  }
}
