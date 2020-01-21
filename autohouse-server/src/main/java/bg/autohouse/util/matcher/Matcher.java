package bg.autohouse.util.matcher;

public interface Matcher<T> {

    boolean matches(Object value);
}