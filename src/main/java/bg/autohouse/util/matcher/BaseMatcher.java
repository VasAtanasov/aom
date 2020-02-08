package bg.autohouse.util.matcher;

public abstract class BaseMatcher<T> implements Matcher<T> {

    protected static boolean isNotNull(Object actual) {
        return actual != null;
    }

}
