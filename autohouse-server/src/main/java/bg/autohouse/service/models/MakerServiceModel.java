package bg.autohouse.service.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MakerServiceModel {
    private Long id;
    private String prettyName;
    private String name;
    @Singular
    private List<ModelServiceModel> models;
}
