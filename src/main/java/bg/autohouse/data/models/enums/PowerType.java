package bg.autohouse.data.models.enums;

import lombok.Getter;

@Getter
public enum PowerType implements Valueable<String> {
    HP("hp"),
    KW("kW");

    private String powerType;

    PowerType(String powerType) {
        this.powerType = powerType;
    }

    @Override
    public String getValue() {
        return powerType;
    }
}
