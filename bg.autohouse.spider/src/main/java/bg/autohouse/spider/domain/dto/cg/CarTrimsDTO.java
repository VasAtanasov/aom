package bg.autohouse.spider.domain.dto.cg;

import lombok.Data;

import java.util.List;

@Data
public class CarTrimsDTO
{
    private String id;
    private List<TrimDTO> trims;
}
