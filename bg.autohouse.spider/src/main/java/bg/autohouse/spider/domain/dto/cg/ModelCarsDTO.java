package bg.autohouse.spider.domain.dto.cg;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ModelCarsDTO implements Serializable
{
    private String id;
    private List<CarDTO> cars;
}
