package bg.autohouse.validation.offer;

import bg.autohouse.data.repositories.ModelRepository;
import bg.autohouse.util.Assert;
import bg.autohouse.web.models.request.VehicleCreateRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class ModelDataValidator
    implements ConstraintValidator<ValidModelData, VehicleCreateRequest> {

  @Autowired private ModelRepository modelRepository;

  @Override
  public boolean isValid(VehicleCreateRequest createRequest, ConstraintValidatorContext context) {
    if (!Assert.has(createRequest)) {
      return false;
    }

    Long makerId = createRequest.getMakerId();
    Long modelId = createRequest.getModelId();

    if (!Assert.has(makerId) || !Assert.has(modelId)) {
      return false;
    }

    return modelRepository.existsByIdAndMakerId(modelId, makerId);
  }
}
