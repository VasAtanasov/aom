package bg.autohouse.service.models;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferCreateServiceModel {

    private String user;
    private String thumbnail;
    private VehicleCreateServiceModel vehicle;
    private Long location;
    private Integer price;
    private String description;
    private List<MultipartFile> files;
}
