package bg.autohouse.spider.util.mappings;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Response {
  private Integer status;
  private String bodyFileName;
  private Map<String, Object> headers;

  public static Response from(Response target) {
    Response response = new Response();
    response.status = target.status;
    response.bodyFileName = target.bodyFileName;
    response.headers = new HashMap<>(target.headers);
    return response;
  }
}
