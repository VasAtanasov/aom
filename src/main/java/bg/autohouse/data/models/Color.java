package bg.autohouse.data.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "colors")
public class Color extends BaseLongEntity {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Builder
    public Color(Long id, String name) {
        super(id);
        this.name = name;
    }
}
