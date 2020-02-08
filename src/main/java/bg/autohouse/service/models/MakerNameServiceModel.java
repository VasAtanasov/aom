package bg.autohouse.service.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MakerNameServiceModel {
    private Long id;
    private String prettyName;
    private String name;
}
