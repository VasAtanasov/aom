package bg.autohouse.data.projections;

public interface PrivateAccountDetails {
  String getFirstName();

  String getLastName();

  String getDisplayedName();

  ContactSummery getContactDetails();

  interface ContactSummery {
    String getPhoneNumber();

    String getWebLink();
  }
}
