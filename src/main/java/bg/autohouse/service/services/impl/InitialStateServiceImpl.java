package bg.autohouse.service.services.impl;

import bg.autohouse.data.models.annotations.CheckboxCriteria;
import bg.autohouse.data.models.annotations.SelectCriteria;
import bg.autohouse.data.models.enums.Textable;
import bg.autohouse.service.models.InitialStateModel;
import bg.autohouse.service.services.InitialStateService;
import bg.autohouse.util.ClassUtils;
import bg.autohouse.util.EnumUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class InitialStateServiceImpl implements InitialStateService {

  private static final String ENUM_PACKAGE = "bg.autohouse.data.models.enums";

  @Override
  @Transactional(readOnly = true)
  public InitialStateModel getInitialState() {
    Map<String, Object> criteria = new HashMap<>();
    List<String> searchCriteriaNamesForCheckboxCriteria = new ArrayList<>();
    List<String> searchCriteriaNamesForSelectCriteria = new ArrayList<>();
    searchCriteriaNamesForSelectCriteria.add("makerName");
    searchCriteriaNamesForSelectCriteria.add("modelName");
    List<String> searchCriteriaNamesForRangeCriteria =
        Arrays.asList("doors", "mileage", "price", "year");
    ClassUtils.find(ENUM_PACKAGE).stream()
        .filter(cls -> cls.isEnum() && Textable.class.isAssignableFrom(cls))
        .forEach(
            cls -> {
              String className = lowerFirstLetter(cls.getSimpleName());
              SelectCriteria selectAnnotation = cls.getAnnotation(SelectCriteria.class);
              CheckboxCriteria checkBoxAnnotation = cls.getAnnotation(CheckboxCriteria.class);
              if (checkBoxAnnotation != null) {
                searchCriteriaNamesForCheckboxCriteria.add(className);
              } else if (selectAnnotation != null) {
                searchCriteriaNamesForSelectCriteria.add(className);
              }
              criteria.put(className, EnumUtils.ENUM_MAP_OF_TEXTABLE(cls));
            });
      return InitialStateModel.builder()
          .metadata(criteria)
          .searchCriteriaNamesForCheckboxCriteria(searchCriteriaNamesForCheckboxCriteria)
          .searchCriteriaNamesForSelectCriteria(searchCriteriaNamesForSelectCriteria)
          .searchCriteriaNamesForRangeCriteria(searchCriteriaNamesForRangeCriteria)
          .build();
  }

  private String lowerFirstLetter(String word) {
    return word.substring(0, 1).toLowerCase() + word.substring(1);
  }
}
