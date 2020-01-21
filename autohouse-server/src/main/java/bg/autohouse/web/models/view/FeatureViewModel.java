package bg.autohouse.web.models.view;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeatureViewModel implements Serializable {
    private Long id;
    private String name;
}
