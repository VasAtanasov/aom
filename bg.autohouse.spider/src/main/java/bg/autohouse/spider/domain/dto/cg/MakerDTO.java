package bg.autohouse.spider.domain.dto.cg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MakerDTO
{
    private String id;
    private String name;
    private boolean popular;
    private List<ModelDTO> models;
}
