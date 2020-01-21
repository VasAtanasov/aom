package bg.autohouse.service.services;

import bg.autohouse.data.models.Image;
import bg.autohouse.service.models.OfferCreateServiceModel;
import bg.autohouse.service.models.OfferDetailsServiceModel;
import bg.autohouse.service.models.OfferListingServiceModel;
import bg.autohouse.web.models.binding.FilterBindingModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface OfferService {

    Page<OfferListingServiceModel> findAll(FilterBindingModel filterBindingModel, Pageable pageable);

    Page<OfferListingServiceModel> findAll(String username, Pageable pageable);

    OfferDetailsServiceModel findById(String id);

    Image uploadImage(MultipartFile multipartFile);

    boolean createOffer(OfferCreateServiceModel offerCreateServiceModel, String userUsername);
}
