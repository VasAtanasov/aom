package bg.autohouse.spider.domain.dto.cg;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MakerDTO implements Serializable
{
    private String id;
    private String name;
    private boolean popular;
    private List<ModelDTO> models;
}
