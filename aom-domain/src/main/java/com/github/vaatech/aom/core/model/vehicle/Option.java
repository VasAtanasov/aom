package com.github.vaatech.aom.core.model.vehicle;

import com.github.vaatech.aom.commons.basic.persistence.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = Option.Persistence.TABLE_NAME,
        indexes = {
                @Index(
                        name = Option.Persistence.INDEX_OPTION_NAME,
                        columnList = Option.Persistence.COLUMN_NAME)
        })
public class Option implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = Persistence.COLUMN_ID, updatable = false, unique = true, nullable = false)
    private Integer id;

    @Column(name = Persistence.COLUMN_NAME)
    private String name;

    @Column(name = Persistence.COLUMN_IMPORTANT)
    private boolean important;

    @Column(name = Persistence.COLUMN_ACTIVE)
    private boolean active;

    @ManyToOne(targetEntity = Trim.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = Persistence.COLUMN_TRIM_ID,
            referencedColumnName = Trim.Persistence.COLUMN_ID,
            foreignKey = @ForeignKey(name = Persistence.FK_OPTIONS_TO_TRIMS_ID))
    private Trim trim;

    public interface Persistence {
        String TABLE_NAME = "option";
        String COLUMN_ID = "id";
        String COLUMN_NAME = "name";
        String COLUMN_IMPORTANT = "important";
        String COLUMN_ACTIVE = "active";
        String COLUMN_TRIM_ID = "trim_id";
        String INDEX_OPTION_NAME = "INDEX_OPTION_NAME";
        String FK_OPTIONS_TO_TRIMS_ID = "FK_OPTIONS_TO_TRIMS_ID";
    }

    public interface Properties {
        String TRIM = "trim";
    }
}
