package bg.autohouse.util.matcher.core;

import bg.autohouse.util.matcher.BaseMatcher;
import bg.autohouse.util.matcher.Matcher;

import static bg.autohouse.util.matcher.core.IsEqual.equalTo;

public class IsNot<T> extends BaseMatcher<T> {
    private final Matcher<T> matcher;

    public IsNot(Matcher<T> matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean matches(Object arg) {
        return !matcher.matches(arg);
    }

    public static <T> Matcher<T> not(Matcher<T> matcher) {
        return new IsNot<T>(matcher);
    }

    public static <T> Matcher<T> not(T value) {
        return not(equalTo(value));
    }
}
