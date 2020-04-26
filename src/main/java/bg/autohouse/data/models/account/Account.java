package bg.autohouse.data.models.account;

import bg.autohouse.data.models.BaseUuidEntity;
import bg.autohouse.data.models.EntityConstants;
import bg.autohouse.data.models.User;
import bg.autohouse.data.models.enums.AccountType;
import bg.autohouse.data.models.geo.Address;
import bg.autohouse.service.models.account.AccountServiceModel;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = EntityConstants.ACCOUNTS)
public class Account extends BaseUuidEntity {

  private static final long serialVersionUID = -3845486410834998722L;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_account_user_id"))
  private User user;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "max_offers_count")
  private int maxOffersCount;

  @Column(name = "description")
  private String description;

  @Column(name = "display_name")
  private String displayName;

  @Column(name = "enabled", nullable = false)
  private boolean enabled = false;

  @Column(name = "closed", nullable = false)
  private boolean closed = false;

  @Column(name = "has_image")
  private boolean hasImage = false;

  @Embedded private ContactDetails contact;

  @Enumerated(EnumType.STRING)
  @Column(name = "account_type", nullable = false)
  private AccountType accountType;

  @OneToOne(
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      mappedBy = "account")
  private Address address;

  @Builder
  private Account(
      User user,
      String firstName,
      String lastName,
      int maxOffersCount,
      String description,
      String displayName,
      String webLink,
      String phoneNumber,
      AccountType accountType) {
    this.user = user;
    this.firstName = firstName;
    this.lastName = lastName;
    this.maxOffersCount = maxOffersCount;
    this.description = description;
    this.displayName = displayName;
    this.contact = ContactDetails.of(phoneNumber, webLink);
    this.accountType = accountType;
    this.enabled = true;
  }

  public static Account createDealerAccount(AccountServiceModel model, User user) {
    Account account =
        Account.builder()
            .user(user)
            .firstName(model.getFirstName())
            .lastName(model.getLastName())
            .maxOffersCount(AccountType.DEALER.resolveMaxOffersCount())
            .displayName(model.getDisplayName())
            .description(model.getDescription())
            .accountType(AccountType.DEALER)
            .phoneNumber(model.getContactDetails().getPhoneNumber())
            .webLink(model.getContactDetails().getWebLink())
            .build();
    user.setHasAccount(true);
    return account;
  }

  public static Account createPrivateAccount(AccountServiceModel model, User user) {
    Account account =
        Account.builder()
            .user(user)
            .firstName(model.getFirstName())
            .lastName(model.getLastName())
            .maxOffersCount(AccountType.PRIVATE.resolveMaxOffersCount())
            .displayName(model.getDisplayName())
            .description(model.getDescription())
            .accountType(AccountType.PRIVATE)
            .phoneNumber(model.getContactDetails().getPhoneNumber())
            .webLink(model.getContactDetails().getWebLink())
            .build();
    user.setHasAccount(true);
    return account;
  }
}
