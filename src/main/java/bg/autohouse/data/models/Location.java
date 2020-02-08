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
@Table(name = "locations")
public class Location extends BaseLongEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Builder
    public Location(Long id, String name) {
        super(id);
        this.name = name;
    }

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<Offer> offers = new ArrayList<>();

    @Transient
    public void addOffer(Offer offer) {
        if (offers == null) {
            offers = new ArrayList<>();
        }
        offers.add(offer);
        offer.setLocation(this);
    }
}
