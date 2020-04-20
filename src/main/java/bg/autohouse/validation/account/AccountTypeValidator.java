package bg.autohouse.validation.account;

import bg.autohouse.data.models.enums.AccountType;
import bg.autohouse.util.Assert;
import bg.autohouse.util.EnumUtils;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AccountTypeValidator implements ConstraintValidator<ValidAccountType, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return validate(value);
  }

  public static boolean validate(final String value) {
    if (!Assert.has(value)) {
      return false;
    }

    Optional<AccountType> accountType = EnumUtils.fromString(value, AccountType.class);

    return accountType.isPresent();
  }
}
