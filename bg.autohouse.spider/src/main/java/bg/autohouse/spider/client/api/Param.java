package bg.autohouse.spider.client.api;

public interface Param<T>
{
    String name();

    T value();

    default String getKey()
    {
        return this.name();
    }

    default T getValue()
    {
        return this.value();
    }

    default T setValue(T value) {
        throw new UnsupportedOperationException("Pair is read only");
    }
}
