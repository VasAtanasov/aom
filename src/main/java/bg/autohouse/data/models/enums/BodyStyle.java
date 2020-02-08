package bg.autohouse.data.models.enums;

import lombok.Getter;

@Getter
public enum BodyStyle implements Valueable<String> {

    SUV("SUV"),
    MPV("MPV"),
    CPO("CPO"),
    HYBRID("Hybrid"),
    SEDAN("Sedan"),
    CROSSOVER("Crossover"),
    LUXURY("Luxury"),
    TRUCK("Truck"),
    PICKUP("Pickup"),
    HATCHBACK("Hatchback"),
    MINIVAN("Minivan"),
    COUPE("Coupe"),
    CONVERTIBLE("Convertible"),
    WAGON("Wagon"),
    SPORT("Sport"),
    VAN("Van"),
    OTHER("Other");

    private String bodyStyle;

    BodyStyle(String bodyStyle) {
        this.bodyStyle = bodyStyle;
    }

    @Override
    public String getValue() {
        return bodyStyle;
    }
}
