package com.github.vaatech.aom.validation;

import com.github.vaatech.aom.feature.vehicle.maker.MakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Transactional(readOnly = true)
public class UniqueMakerNameValidator implements ConstraintValidator<UniqueMakerName, String> {

  @Autowired private MakerRepository makerRepository;

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return !makerRepository.existsByName(value);
  }
}
