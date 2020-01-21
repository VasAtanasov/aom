package bg.autohouse.util.matcher.core;

import bg.autohouse.util.matcher.Matcher;
import bg.autohouse.util.matcher.NumberMatcher;

public class LessThanInteger extends NumberMatcher<Integer> {

    private final Integer value;

    public LessThanInteger(Integer value) {
        this.value = value;
    }

    @Override
    protected boolean matchesNumber(Integer item) {
        return item.compareTo(value) < 0;
    }


    public static Matcher<Integer> isLessThanInt(Integer value) {
        return new LessThanInteger(value);
    }
}
