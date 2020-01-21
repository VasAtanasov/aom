package bg.autohouse.service.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfferListingServiceModel {
    private String id;
    private String vehicleState;
    private Integer vehicleYear;
    private String vehicleMakerPrettyName;
    private String vehicleModelPrettyName;
    private String vehicleEngineFuelType;
    private Integer vehicleMileage;
    private Integer price;
    private String vehicleTransmission;
    private String locationName;
    private String vehicleBodyStyle;
    private String auditCreatedOn;
    private String thumbnail;
    private String vehicleEnginePowerType;
    private Integer vehicleEnginePower;
}
