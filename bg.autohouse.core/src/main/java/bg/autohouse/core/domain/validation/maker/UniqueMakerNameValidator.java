package bg.autohouse.core.domain.validation.maker;

import bg.autohouse.core.domain.repositories.MakerRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class UniqueMakerNameValidator implements ConstraintValidator<UniqueMakerName, String> {

  @Autowired private MakerRepository makerRepository;

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return !makerRepository.existsByName(value);
  }
}
