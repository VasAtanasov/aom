package bg.autohouse.util.matcher.core;

import bg.autohouse.util.matcher.Matcher;
import bg.autohouse.util.matcher.NumberMatcher;

public class IsNegativeInteger extends NumberMatcher<Integer> {

    @Override
    protected boolean matchesNumber(Integer item) {
        return item < 0;
    }

    public static Matcher<Integer> isNegativeInt() {
        return new IsNegativeInteger();
    }
}
