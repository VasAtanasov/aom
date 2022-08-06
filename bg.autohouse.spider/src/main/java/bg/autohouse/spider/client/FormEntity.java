package bg.autohouse.spider.client;

import java.util.ArrayList;
import java.util.List;

public class FormEntity {
  private final List<FormField> formData = new ArrayList<>();

  FormEntity() {}

  public static FormEntity form(String key, Object value) {
    FormEntity formEntity = new FormEntity();
    formEntity.addParameter(key, value);
    return formEntity;
  }

  public static FormEntity form() {
    return new FormEntity();
  }

  public FormEntity addParameter(String key, Object value) {
    formData.add(new FormField(key, value));
    return this;
  }

  public List<FormField> data() {
    return new ArrayList<>(formData);
  }
}
