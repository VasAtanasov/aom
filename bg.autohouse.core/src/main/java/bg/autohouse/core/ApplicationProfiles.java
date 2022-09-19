package bg.autohouse.core;

public final class ApplicationProfiles {
  public static final String INMEMORY =
      "default"; // default, indicates application is running locally & expects resources in-memory
  public static final String DEV = "dev"; // local, used for all dev work
  public static final String STAGING = "staging";
  public static final String PRODUCTION = "production";
}
