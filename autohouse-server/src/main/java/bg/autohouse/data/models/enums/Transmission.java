package bg.autohouse.data.models.enums;

import lombok.Getter;

@Getter
public enum Transmission implements Valueable<String> {

    AUTOMATIC("Automatic"),
    SEMI_AUTOMATIC("Semi-automatic"),
    MANUAL("Manual");

    private String transmission;

    Transmission(String transmission) {
        this.transmission = transmission;
    }

    @Override
    public String getValue() {
        return transmission;
    }
}
