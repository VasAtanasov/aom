package bg.autohouse.spider.client;

public class QueryParameter extends AbstractParameter<String> {
  public QueryParameter(String key, String value) {
    super(key, value);
  }

  public static QueryParameter of(String key, String value) {
    return new QueryParameter(key, value);
  }
}
