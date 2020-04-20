package bg.autohouse.errors;

public class AccountDisabledOrClosed extends RuntimeException {

  private static final long serialVersionUID = -7691187825982199055L;

  public AccountDisabledOrClosed(String message) {
    super(message);
  }

  public AccountDisabledOrClosed() {}
}
