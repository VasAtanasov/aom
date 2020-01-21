package bg.autohouse.service.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleCreateServiceModel {

    private String vehicleType;
    private String state;
    private Long maker;
    private Long model;
    private Integer month;
    private Integer year;
    private Integer mileage;
    private String bodyStyle;
    private EngineCreateServiceModel engine;
    private String transmission;
    private String drive;
    private Long interiorColor;
    private Long exteriorColor;
    private Integer seats;
    private Integer doors;
    private Integer owners;
    private List<Long> features;
    private Boolean hasAccident;
}
