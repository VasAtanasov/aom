package bg.autohouse.api.domain.model.common;

import java.io.Serializable;

public interface Identifiable<T> extends Serializable
{
    default T getId()
    {
        return null;
    }

    default void setId(T id)
    {
        throw new UnsupportedOperationException();
    }
}
