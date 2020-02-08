package bg.autohouse.web.rest;

import bg.autohouse.service.models.OfferListingServiceModel;
import bg.autohouse.service.services.OfferService;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.models.binding.FilterBindingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OfferRestController {

    private final OfferService offerService;
    private final ModelMapperWrapper modelMapper;

    @Autowired
    public OfferRestController(OfferService offerService, ModelMapperWrapper modelMapper) {
        this.offerService = offerService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/offers/all", consumes = "application/json")
    public ResponseEntity<Page<OfferListingServiceModel>> getOffersSorted(@RequestBody FilterBindingModel filterBindingModel, Pageable pageable) {
        Page<OfferListingServiceModel> offers = offerService.findAll(filterBindingModel, pageable);
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }
}
