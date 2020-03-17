package bg.autohouse.web.controllers;

import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;

import bg.autohouse.config.WebConfiguration;
import bg.autohouse.data.models.Offer;
import bg.autohouse.data.models.enums.FuelType;
import bg.autohouse.service.models.OfferTopServiceModel;
import bg.autohouse.service.services.OfferService;
import bg.autohouse.util.EnumUtils;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.models.request.OfferCreateRequest;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WebConfiguration.OFFERS)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OfferController {

  private final OfferService offerService;
  private final ModelMapperWrapper modelMapper;

  // TODO authenticated users can create offers add authenticated principals
  @PostMapping(
      produces = {APP_V1_MEDIA_TYPE_JSON},
      consumes = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> createOffer(@Valid @RequestBody OfferCreateRequest createRequest) {
    modelMapper.map(
        createRequest,
        Offer.class,
        new PropertyMap<OfferCreateRequest, Offer>() {
          @Override
          protected void configure() {
            FuelType fuelType =
                EnumUtils.fromString(
                        createRequest.getVehicle().getEngine().getFuelType(), FuelType.class)
                    .get();
            map().getVehicle().getEngine().setFuelType(fuelType);
          }
        });

    return ResponseEntity.ok().build();
  }

  @GetMapping(
      value = "/{fuelType}",
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> getByFUelType(@PathVariable String fuelType) {

    FuelType fuelType2 = EnumUtils.fromString(fuelType, FuelType.class).get();
    List<OfferTopServiceModel> offer = offerService.getOffersByFuelType(fuelType2);
    return ResponseEntity.ok().body(offer);
  }
}
