package bg.autohouse.spider.util.mappings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class Request {
  private String url;
  private String method;
  private List<Map<String, String>> bodyPatterns;

  public static Request from(Request target) {
    Request request = new Request();
    request.url = target.url;
    request.method = target.method;
    if (target.bodyPatterns != null) {
      request.bodyPatterns = new ArrayList<>(target.bodyPatterns);
    }
    return request;
  }
}
