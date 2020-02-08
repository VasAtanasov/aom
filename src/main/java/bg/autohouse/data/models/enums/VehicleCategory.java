package bg.autohouse.data.models.enums;

public enum VehicleCategory implements Valueable<String> {

    CAR(Values.CAR),
    TRUCK(Values.TRUCK);

    private String value;

    VehicleCategory(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public static class Values {
        public static final String CAR = "car";
        public static final String TRUCK = "truck";
    }
}
