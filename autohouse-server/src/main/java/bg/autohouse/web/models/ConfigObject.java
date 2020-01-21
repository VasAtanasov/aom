package bg.autohouse.web.models;

import bg.autohouse.web.models.binding.FilterBindingModel;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfigObject implements Serializable {
    private FilterBindingModel filterBindingModel;
    private int pageSize;
    private int pageNumber;
    private String sort;
}
