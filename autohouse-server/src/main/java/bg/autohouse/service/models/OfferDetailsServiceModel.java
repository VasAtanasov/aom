package bg.autohouse.service.models;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferDetailsServiceModel {
    private String id;
    private String userId;
    @Builder.Default
    private List<ImageDetailsServiceModel> images = new ArrayList<>();
    private Integer price;
    private String priceModifiedOn;
    private String auditModifiedOn;
    private String auditCreatedOn;
    private Long locationId;
    private String locationName;
    private Integer hitCount;
    private String description;
    private String vehicleId;
    private String vehicleVehicleType;
    private String vehicleState;
    private String vehicleMakerPrettyName;
    private String vehicleModelPrettyName;
    private Integer vehicleMonth;
    private Integer vehicleYear;
    private String vehicleBodyStyle;
    private Integer vehicleMileage;
    private String vehicleTransmission;
    private String vehicleDrive;
    private Long vehicleInteriorColorId;
    private String vehicleInteriorColorName;
    private Long vehicleExteriorColorId;
    private String vehicleExteriorColorName;
    private Integer vehicleSeats;
    private Integer vehicleDoors;
    private Integer vehicleOwners;
    private Boolean vehicleHasAccident;
    @Builder.Default
    private List<FeatureServiceModel> vehicleFeatures = new ArrayList<>();
    private String vehicleEngineId;
    private String vehicleEngineModification;
    private String vehicleEngineFuelType;
    private Integer vehicleEngineCylinders;
    private Integer vehicleEnginePower;
    private String vehicleEnginePowerType;
    private String vehicleEngineEuroStandard;
}
