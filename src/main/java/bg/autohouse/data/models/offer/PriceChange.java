package bg.autohouse.data.models.offer;

import bg.autohouse.data.models.BaseLongEntity;
import bg.autohouse.data.models.EntityConstants;
import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = EntityConstants.PRICE_CHANGE)
public class PriceChange extends BaseLongEntity {

  private static final long serialVersionUID = 7489138272903016439L;

  @ManyToOne(targetEntity = Offer.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "offer_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_price_change_offer_id"))
  private Offer offer;

  @Column(name = "price", nullable = false)
  private Integer price;

  @Column(name = "delta")
  private Integer delta;
}
