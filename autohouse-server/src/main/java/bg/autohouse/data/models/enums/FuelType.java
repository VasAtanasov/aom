package bg.autohouse.data.models.enums;

import lombok.Getter;

@Getter
public enum FuelType implements Valueable<String> {

    GASOLINE("Gasoline"),
    DIESEL("Diesel"),
    ETHANOL("Ethanol"),
    ELECTRIC("Electric"),
    HYDROGEN("Hydrogen"),
    LPG("LPG"),
    CNG("CNG"),
    ELECTRIC_GASOLINE("Electric/Gasoline"),
    OTHERS("Others"),
    ELECTRIC_DIESEL("Electric/Diesel");

    private String fuelType;

    FuelType(String type) {
        fuelType = type;
    }

    @Override
    public String getValue() {
        return fuelType;
    }
}
