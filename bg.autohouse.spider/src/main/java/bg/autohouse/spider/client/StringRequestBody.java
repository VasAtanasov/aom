package bg.autohouse.spider.client;

class StringRequestBody extends RequestBody<String> {

  StringRequestBody(String body) {
    super(body, "plain/text");
  }

}
