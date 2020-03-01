package bg.autohouse.util;

public interface JsonParser {

  <O> O parseJson(Class<O> objectClass, String text);

  String toString(Object obj);
}
