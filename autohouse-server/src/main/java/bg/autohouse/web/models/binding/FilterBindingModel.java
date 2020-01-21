package bg.autohouse.web.models.binding;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class FilterBindingModel implements Serializable {
    private Long maker;
    private Long model;
    private String modification;
    private String bodyStyle;
    private Integer firstRegistrationFrom;
    private Integer firstRegistrationTo;
    private Integer priceFrom;
    private Integer priceTo;
    private String fuelType;
    private Integer mileageFrom;
    private Integer mileageTo;
    private String powerType;
    private Integer powerFrom;
    private Integer powerTo;
    private String transmission;
    private Integer cylinders;
    private String drive;
    private Integer doors;
    private Integer seatsFrom;
    private Integer seatsTo;
    private String state;
    private Long exteriorColor;
    private Long interiorColor;
    private Integer previousOwners;
    private Boolean hasAccident;
    private String euroStandard;
    private Integer onlineSince;
    private Long location;
    @Builder.Default
    private List<Long> features = new ArrayList<>();
}
