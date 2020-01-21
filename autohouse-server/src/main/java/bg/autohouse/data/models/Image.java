package bg.autohouse.data.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images")
@Builder
public class Image extends BaseUuidEntity {

    @Column
    private String thumbnailUrl;

    @Column(nullable = false)
    private String url;

    @ManyToOne(targetEntity = Offer.class)
    @JoinColumn(
            name = "offer_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Offer offer;

}
