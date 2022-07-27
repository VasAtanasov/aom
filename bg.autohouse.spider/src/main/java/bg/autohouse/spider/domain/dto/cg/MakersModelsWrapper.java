package bg.autohouse.spider.domain.dto.cg;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class MakersModelsWrapper implements Serializable
{
    private static final String MAKERS_KEY = "makers";

    private final Map<String, List<MakerDTO>> allMakerModels;

    public MakersModelsWrapper(@JsonProperty("allMakerModels") Map<String, List<MakerDTO>> allMakerModels)
    {
        this.allMakerModels = allMakerModels;
    }

    @JsonIgnore
    public List<MakerDTO> makers()
    {
        return this.allMakerModels.computeIfAbsent(MAKERS_KEY, k -> List.of());
    }
}
