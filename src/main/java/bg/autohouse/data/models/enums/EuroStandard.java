package bg.autohouse.data.models.enums;

import lombok.Getter;

@Getter
public enum EuroStandard implements Valueable<String> {

    EURO1("Euro 1"),
    EURO2("Euro 2"),
    EURO3("Euro 3"),
    EURO4("Euro 4"),
    EURO5("Euro 5"),
    EURO6("Euro 6");

    private String value;

    EuroStandard(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
