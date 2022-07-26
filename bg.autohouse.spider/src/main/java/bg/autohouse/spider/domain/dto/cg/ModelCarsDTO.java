package bg.autohouse.spider.domain.dto.cg;

import lombok.Data;

import java.util.List;

@Data
public class ModelCarsDTO
{
    private String id;
    private List<CarDTO> cars;
}
