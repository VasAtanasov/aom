package bg.autohouse.service.validations;

public enum Required implements FieldPresence {
  YES,
  VOLUNTARY;

  @Override
  public boolean nonNullValueRequired() {
    return this == YES;
  }
}
