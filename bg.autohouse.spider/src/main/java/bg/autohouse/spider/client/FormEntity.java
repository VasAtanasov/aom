package bg.autohouse.spider.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class FormEntity
{
    private Collection<FormParameter> formData;

    private FormEntity()
    {
        formData = new ArrayList<>();
    }

    public static FormEntity of(String key, Object value)
    {
        FormEntity formEntity = new FormEntity();
        formEntity.addParameter(key, value);
        return formEntity;
    }

    public static FormEntity newFormEntity()
    {
        return new FormEntity();
    }

    public void addParameter(String key, Object value)
    {
        formData.add(new FormParameter(key, value));
    }

    public Collection<FormParameter> data()
    {
        return Collections.unmodifiableCollection(formData);
    }
}
