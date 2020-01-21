package bg.autohouse.data.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper=true)
@Entity
@Table(name = "features")
public class Feature extends BaseLongEntity {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Builder
    public Feature(Long id, String name) {
        super(id);
        this.name = name;
    }
}
