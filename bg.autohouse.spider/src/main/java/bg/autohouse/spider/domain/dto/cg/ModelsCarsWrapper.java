package bg.autohouse.spider.domain.dto.cg;


import java.util.ArrayList;
import java.util.List;

public class ModelsCarsWrapper
{
    private List<ModelDTO> models;

    public List<ModelDTO> getModels()
    {
        return models == null ? new ArrayList<>() : models;
    }

    public void setModels(List<ModelDTO> models)
    {
        this.models = models;
    }
}
