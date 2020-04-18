package bg.autohouse.errors;

import java.util.function.Supplier;

public class ExceptionSupplier {
  public static Supplier<NoSuchUserException> noSuchUser = () -> new NoSuchUserException();

  public static Supplier<UserRegistrationDisabledException> userDisabled =
      () -> new UserRegistrationDisabledException();

  public static Supplier<UsernamePasswordLoginFailedException> loginFailed =
      () -> new UsernamePasswordLoginFailedException();

  public static Supplier<AccountNotFoundException> noAccountFound =
      () -> new AccountNotFoundException();

  public static Supplier<AccountDisabledOrClosed> accountInactive =
      () -> new AccountDisabledOrClosed();
}
