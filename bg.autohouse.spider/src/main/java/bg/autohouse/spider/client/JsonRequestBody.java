package bg.autohouse.spider.client;

import java.io.InputStream;

class JsonRequestBody<T> extends RequestBody {

  JsonRequestBody(InputStream body) {
    super(body, "application/json");
  }

}
