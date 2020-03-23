package bg.autohouse.validation.user;

import bg.autohouse.data.models.enums.Seller;
import bg.autohouse.util.Assert;
import bg.autohouse.util.EnumUtils;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SellerValidator implements ConstraintValidator<ValidSeller, String> {

  @Override
  public boolean isValid(String values, ConstraintValidatorContext context) {
    return validate(values);
  }

  public static boolean validate(final String value) {
    if (!Assert.has(value)) {
      return false;
    }

    Optional<Seller> euroStandard = EnumUtils.fromString(value, Seller.class);

    return euroStandard.isPresent();
  }
}
