package bg.autohouse.data.models.enums;

import lombok.Getter;

@Getter
public enum Drive implements Valueable<String> {

    FRONT_WHEEL_DRIVE("Front Wheel Drive"),
    REAR_WHEEL_DRIVE("Rear Wheel Drive"),
    FOUR_WHEEL_DRIVE("Four Wheel Drive"),
    ALL_WHEEL_DRIVE("All Wheel Drive");

    private String drive;

    Drive(String drive) {
        this.drive = drive;
    }

    @Override
    public String getValue() {
        return drive;
    }
}
