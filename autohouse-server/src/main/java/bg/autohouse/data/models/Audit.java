package bg.autohouse.data.models;

import bg.autohouse.data.models.converters.LocalDateTimeAttributeConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Audit {
    @Column(name = "created_on", nullable = false)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime createdOn;

    @Column(name = "modified_on")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime modifiedOn;
}