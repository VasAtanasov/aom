package bg.autohouse.util.matcher;

public abstract class NumberMatcher<T extends Number> extends BaseMatcher<T> {

    protected abstract boolean matchesNumber(T item);

    @Override
    @SuppressWarnings("unchecked")
    public boolean matches(Object value) {
        return value != null
                && matchesNumber((T) value);
    }
}
