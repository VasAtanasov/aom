package bg.autohouse.util.matcher.core;

import bg.autohouse.util.matcher.Matcher;
import bg.autohouse.util.matcher.NumberMatcher;

public class GreaterThanInteger extends NumberMatcher<Integer> {

    private final Integer value;

    public GreaterThanInteger(Integer value) {
        this.value = value;
    }

    @Override
    protected boolean matchesNumber(Integer item) {
        return item.compareTo(value) > 0;
    }


    public static Matcher<Integer> isGreaterThanInt(Integer value) {
        return new GreaterThanInteger(value);
    }
}
