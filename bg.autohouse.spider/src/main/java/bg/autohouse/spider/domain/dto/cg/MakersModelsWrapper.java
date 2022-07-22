package bg.autohouse.spider.domain.dto.cg;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class MakersModelsWrapper
{
    private final Map<String, List<MakerDTO>> allMakerModels;

    public Map<String, List<MakerDTO>> getAllMakerModels()
    {
        return allMakerModels;
    }

    public MakersModelsWrapper(@JsonProperty("allMakerModels") Map<String, List<MakerDTO>> allMakerModels)
    {
        this.allMakerModels = allMakerModels;
    }
}
