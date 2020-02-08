package bg.autohouse.util.matcher;

public class MatcherAssert {

    public static <T> boolean assertThat(T actual, Matcher<? super T> matcher) {
        return matcher.matches(actual);
    }


}
