package bg.autohouse.spider.domain.dto.cg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO
{
    private String id;
    private int year;
    //  private List<Trim> trims = new ArrayList<>();
}
