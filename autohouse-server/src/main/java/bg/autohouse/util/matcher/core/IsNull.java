package bg.autohouse.util.matcher.core;

import bg.autohouse.util.matcher.BaseMatcher;
import bg.autohouse.util.matcher.Matcher;

import static bg.autohouse.util.matcher.Matchers.not;

public class IsNull<T> extends BaseMatcher<T> {

    @Override
    public boolean matches(Object value) {
        return value == null;
    }

    public static Matcher<Object> nullValue() {
        return new IsNull<Object>();
    }

    public static Matcher<Object> notNullValue() {
        return not(nullValue());
    }


    public static <T> Matcher<T> nullValue(Class<T> type) {
        return new IsNull<T>();
    }

    public static <T> Matcher<T> notNullValue(Class<T> type) {
        return not(nullValue(type));
    }
}