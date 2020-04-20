package bg.autohouse.data.models.enums;

public enum UserLogType {
  CREATED_IN_DB("User entity created"),
  GRANTED_SYSTEM_ROLE("User was granted system role"),
  REVOKED_SYSTEM_ROLE("User had system role removed"),

  ADMIN_CHANGED_PASSWORD("A system admin reset the user's password"),
  USER_CHANGED_PASSWORD("User change password"),
  USER_EMAIL_CHANGED("User changed their email address"),
  USER_PHONE_CHANGED("User changed their phone number"),
  USER_DETAILS_CHANGED("User changed non-sensitive details"),

  ADDED_ADDRESS("User added address"),
  CHANGED_ADDRESS("User changed address"),
  REMOVED_ADDRESS("User removed addres"),

  USER_SESSION("User initiated a session"),
  USER_ADDED_PRIVATE_ACCOUNT("User added private seller account"),
  USER_SKIPPED_NAME("User preferred not to set name"),
  USER_SKIPPED_PROVINCE("User preferred not to set a province"),
  USER_CHANGE_PROFILE_PHOTO("User change own profile photo");

  private final String text;

  UserLogType(final String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }
}
