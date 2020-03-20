package bg.autohouse.service.services;

import bg.autohouse.service.models.OfferServiceModel;
import bg.autohouse.service.models.OfferTopServiceModel;
import bg.autohouse.web.models.request.FilterRequest;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OfferService {
  List<OfferTopServiceModel> getTopOffers();

  Page<OfferServiceModel> searchOffers(FilterRequest filterRequest, Pageable pageable);
}
