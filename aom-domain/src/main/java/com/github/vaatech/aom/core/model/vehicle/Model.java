package com.github.vaatech.aom.core.model.vehicle;

import com.github.vaatech.aom.commons.basic.persistence.model.BaseEntity;
import com.github.vaatech.aom.commons.validation.ModelName;
import com.github.vaatech.aom.commons.validation.ValidationMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = Model.Persistence.TABLE_NAME,
        indexes = {
                @Index(name = Model.Persistence.INDEX_MODEL_NAME, columnList = Model.Persistence.COLUMN_NAME)
        })
public class Model implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = Persistence.COLUMN_ID, updatable = false, nullable = false)
    private Integer id;

    @Column(name = Persistence.COLUMN_NAME)
    @ModelName
    private String name;

    @NotNull(message = ValidationMessages.MAKER_NULL)
    @ManyToOne(targetEntity = Maker.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = Persistence.COLUMN_MAKER_ID,
            referencedColumnName = Maker.Persistence.COLUMN_ID,
            foreignKey = @ForeignKey(name = Persistence.FK_MODEL_TO_MAKE_ID))
    private Maker maker;

    @OneToMany(
            mappedBy = ModelYear.Properties.MODEL,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<ModelYear> modelYears = new HashSet<>();

    public interface Persistence {
        String COLUMN_ID = "id";
        String TABLE_NAME = "model";
        String COLUMN_NAME = "name";
        String COLUMN_MAKER_ID = "maker_id";
        String FK_MODEL_TO_MAKE_ID = "FK_MODEL_TO_MAKE_ID";
        String INDEX_MODEL_NAME = "INDEX_MODEL_NAME";
    }

    public interface Properties {
        String MAKER = "maker";
    }
}
