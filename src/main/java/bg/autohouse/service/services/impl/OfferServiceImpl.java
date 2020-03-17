package bg.autohouse.service.services.impl;

import bg.autohouse.data.models.enums.FuelType;
import bg.autohouse.data.repositories.OfferRepository;
import bg.autohouse.data.specifications.OfferSpecifications;
import bg.autohouse.service.models.OfferTopServiceModel;
import bg.autohouse.service.services.OfferService;
import bg.autohouse.util.ModelMapperWrapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OfferServiceImpl implements OfferService {

  private final OfferRepository offerRepository;
  private final ModelMapperWrapper modelMapper;

  @Override
  public List<OfferTopServiceModel> getTopOffers() {
    Sort sort = Sort.by("createdAt").descending();
    Pageable pageable = PageRequest.of(0, 20, sort);
    List<OfferTopServiceModel> topOffers =
        offerRepository
            .findLatestOffers(pageable)
            .map(offer -> modelMapper.map(offer, OfferTopServiceModel.class))
            .collect(Collectors.toUnmodifiableList());
    return topOffers;
  }

  @Override
  public List<OfferTopServiceModel> getOffersByFuelType(FuelType fuelType) {
    return modelMapper.mapAll(
        offerRepository.findAll(OfferSpecifications.hasFuelType(fuelType)),
        OfferTopServiceModel.class);
  }
}
