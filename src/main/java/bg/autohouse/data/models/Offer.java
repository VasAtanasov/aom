package bg.autohouse.data.models;

import bg.autohouse.data.listeners.AuditListener;
import bg.autohouse.data.models.converters.LocalDateTimeAttributeConverter;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "offers")
@EntityListeners(AuditListener.class)
public class Offer extends BaseUuidEntity implements Auditable {

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            nullable = false
    )
    private User user;

    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Image> images = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "offer")
    private Vehicle vehicle;

    @Column(name = "price", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT '0'")
    private Integer price;

    @Column(name = "price_modified_on")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime priceModifiedOn;

    @Embedded
    private Audit audit;

    @Column(name = "valid_until", nullable = false)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime validUntil;

    @ManyToOne(targetEntity = Location.class)
    @JoinColumn(
            name = "location_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Location location;

    @Column(name = "hit_count", columnDefinition = "INT UNSIGNED DEFAULT '0'")
    private Integer hitCount;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean isActive;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean isDeleted;

    @Column(name = "is_expired", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean isExpired;

    @Transient
    private Integer previousPrice;

    @PrePersist
    public void setActive() {
        isActive = true;
        isDeleted = false;
        isExpired = false;
        validUntil = LocalDateTime.now().plusMonths(3);
        hitCount = 0;
    }

    @PreUpdate
    private void doBeforeUpdate() {
        int areEqualPrices = Integer.compare(previousPrice, price);
        if (areEqualPrices != 0) {
            priceModifiedOn = LocalDateTime.now();
        }
    }

    @PostLoad
    private void storePriceState() {
        previousPrice = price;
    }

    @Transient
    public void addImage(Image image) {
        if (images == null) {
            images = new ArrayList<>();
        }
        if (thumbnail == null) {
            thumbnail = image.getUrl();
        }
        images.add(image);
        image.setOffer(this);
    }
}