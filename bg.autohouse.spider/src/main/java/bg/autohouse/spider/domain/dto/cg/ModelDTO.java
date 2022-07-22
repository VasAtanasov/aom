package bg.autohouse.spider.domain.dto.cg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelDTO
{
    private String id;
    private String name;
    private boolean popular;
}
