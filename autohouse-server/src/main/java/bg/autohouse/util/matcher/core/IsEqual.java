package bg.autohouse.util.matcher.core;

import bg.autohouse.util.matcher.BaseMatcher;
import bg.autohouse.util.matcher.Matcher;

public class IsEqual<T> extends BaseMatcher<T> {
    private final Object expectedValue;

    public IsEqual(T equalArg) {
        expectedValue = equalArg;
    }

    @Override
    public boolean matches(Object actualValue) {
        return areEqual(actualValue, expectedValue);
    }

    public static <T> Matcher<T> equalTo(T operand) {
        return new IsEqual<>(operand);
    }

    private static boolean areEqual(Object actual, Object expected) {
        if (actual == null) {
            return expected == null;
        }

        return actual.equals(expected);
    }

    public static Matcher<Object> equalToObject(Object operand) {
        return new IsEqual<>(operand);
    }
}