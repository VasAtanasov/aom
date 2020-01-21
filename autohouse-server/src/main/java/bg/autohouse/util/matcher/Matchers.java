package bg.autohouse.util.matcher;

import bg.autohouse.util.matcher.core.*;

public class Matchers {

    public static <T> Matcher<T> is(Matcher<T> matcher) {
        return Is.is(matcher);
    }

    public static <T> Matcher<T> is(T value) {
        return Is.is(value);
    }

    public static Matcher<Integer> isNegativeInt() {
        return IsNegativeInteger.isNegativeInt();
    }

    public static Matcher<Integer> greaterThanInt(Integer value) {
        return GreaterThanInteger.isGreaterThanInt(value);
    }

    public static Matcher<Integer> lessThanInt(Integer value) {
        return LessThanInteger.isLessThanInt(value);
    }

    public static <T> Matcher<T> equalTo(T operand) {
        return IsEqual.equalTo(operand);
    }

    public static <T> Matcher<T> not(Matcher<T> matcher) {
        return IsNot.not(matcher);
    }


    public static <T> Matcher<T> not(T value) {
        return IsNot.not(value);
    }

    public static Matcher<java.lang.Object> notNullValue() {
        return IsNull.notNullValue();
    }

    public static <T> Matcher<T> notNullValue(java.lang.Class<T> type) {
        return IsNull.notNullValue(type);
    }


    public static Matcher<java.lang.Object> nullValue() {
        return IsNull.nullValue();
    }


    public static <T> Matcher<T> nullValue(java.lang.Class<T> type) {
        return IsNull.nullValue(type);
    }
}
