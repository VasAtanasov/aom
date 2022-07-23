package bg.autohouse.spider.client;

class JsonRequestBody<T> extends RequestBody<T> {

  JsonRequestBody(T body) {
    super(body, "application/json");
  }

}
