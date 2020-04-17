package bg.autohouse.data.models.enums;

public enum UserLogType {
  CREATED_IN_DB("user entity created"),
  GRANTED_SYSTEM_ROLE("User was granted system role"),
  REVOKED_SYSTEM_ROLE("User had system role removed"),

  INITIATED_USSD("user initiated first USSD session"),
  CREATED_WEB("user created a web profile"),
  REGISTERED_ANDROID("user registered on Android"),

  ADMIN_CHANGED_PASSWORD("A system admin reset the user's password"),
  USER_CHANGED_PASSWORD("User change password"),
  USER_EMAIL_CHANGED("User changed their email address"),
  USER_PHONE_CHANGED("User changed their phone number"),
  USER_DETAILS_CHANGED("User changed non-sensitive details"),

  ADDED_ADDRESS("user added address"),
  CHANGED_ADDRESS("user changed address"),
  REMOVED_ADDRESS("user removed addres"),

  USER_SESSION("user initiated a session"),
  USER_SKIPPED_NAME("user preferred not to set name"),
  USER_SKIPPED_PROVINCE("user preferred not to set a province"),
  USER_CHANGE_PROFILE_PHOTO("user change own profile photo");

  private final String text;

  UserLogType(final String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }
}
