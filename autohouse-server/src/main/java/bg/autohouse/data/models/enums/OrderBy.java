package bg.autohouse.data.models.enums;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public enum OrderBy implements Valueable<String> {

    LATEST("Latest offers first", "auditCreatedOn,desc"),
    PRICE_ASC("Price ascending", "price"),
    PRICE_DSC("Price descending", "price,desc"),
    MILEAGE_ASC("Mileage ascending", "vehicleMileage"),
    MILEAGE_DSC("Mileage descending", "vehicleMileage,desc"),
    POWER_ASC("Power ascending", "vehicleEnginePower"),
    POWER_DSC("Power descending", "vehicleEnginePower,desc"),
    REGISTRATION_ASC("First registration ascending", "vehicleYear"),
    REGISTRATION_DSC("First registration descending", "vehicleYear,desc");

    private String value;
    private String text;

    OrderBy(String text, String value) {
        this.value = value;
        this.text = text;
    }

    @Override
    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    public static Map<String, String> getTextAndValue() {
        Map<String, String> textValueMap = new LinkedHashMap<>();
        Stream.of(OrderBy.values()).forEach(orderBy -> {
            textValueMap.put(orderBy.text, orderBy.value);
        });
        return textValueMap;
    }
}