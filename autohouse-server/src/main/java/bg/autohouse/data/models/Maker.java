package bg.autohouse.data.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "makers")
public class Maker extends BaseLongEntity {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "pretty_name", unique = true, nullable = false)
    private String prettyName;

    @OneToMany(mappedBy = "maker", fetch = FetchType.LAZY)
    private List<Model> models;

    @Transient
    public void addModel(Model model) {
        if (models == null) {
            models = new ArrayList<>();
        }
        models.add(model);
        model.setMaker(this);
    }

    @Builder
    public Maker(Long id, String name, String prettyName) {
        super(id);
        this.name = name;
        this.prettyName = prettyName;
    }
}
