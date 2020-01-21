package bg.autohouse.data.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "models")
public class Model extends BaseLongEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "pretty_name", nullable = false)
    private String prettyName;

    @ManyToOne(targetEntity = Maker.class, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "maker_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Maker maker;

    @Builder
    public Model(Long id, String name, String prettyName, Maker maker) {
        super(id);
        this.name = name;
        this.prettyName = prettyName;
        this.maker = maker;
    }
}
