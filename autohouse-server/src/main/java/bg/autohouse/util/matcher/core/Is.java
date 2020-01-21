package bg.autohouse.util.matcher.core;

import bg.autohouse.util.matcher.BaseMatcher;
import bg.autohouse.util.matcher.Matcher;

import static bg.autohouse.util.matcher.core.IsEqual.equalTo;

public class Is<T> extends BaseMatcher<T> {
    private final Matcher<T> matcher;

    public Is(Matcher<T> matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean matches(Object arg) {
        return matcher.matches(arg);
    }

    public static <T> Matcher<T> is(Matcher<T> matcher) {
        return new Is<>(matcher);
    }

    public static <T> Matcher<T> is(T value) {
        return is(equalTo(value));
    }

}