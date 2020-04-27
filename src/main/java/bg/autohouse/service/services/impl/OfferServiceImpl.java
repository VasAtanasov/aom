package bg.autohouse.service.services.impl;

import static org.springframework.data.jpa.domain.Specification.where;

import bg.autohouse.data.models.Filter;
import bg.autohouse.data.models.offer.Offer;
import bg.autohouse.data.repositories.OfferRepository;
import bg.autohouse.data.specifications.OfferSpecifications;
import bg.autohouse.service.models.offer.OfferServiceModel;
import bg.autohouse.service.services.OfferService;
import bg.autohouse.util.Assert;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.models.request.FilterRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OfferServiceImpl implements OfferService {

  private final OfferRepository offerRepository;
  private final ModelMapperWrapper modelMapper;

  @Override
  @Transactional(readOnly = true)
  public List<OfferServiceModel> getTopOffers() {
    Sort sort = Sort.by("createdAt").descending();
    Pageable pageable = PageRequest.of(0, 20, sort);
    List<OfferServiceModel> topOffers =
        offerRepository
            .findLatestOffers(pageable)
            .map(offer -> modelMapper.map(offer, OfferServiceModel.class))
            .collect(Collectors.toUnmodifiableList());
    return topOffers;
  }

  // TODO refactor filter request to add maker and models names
  @Override
  @Transactional(readOnly = true)
  public Page<OfferServiceModel> searchOffers(FilterRequest filterRequest, Pageable pageable) {
    Objects.requireNonNull(filterRequest);
    Filter filter = modelMapper.map(filterRequest, Filter.class);
    if (Assert.has(filterRequest.getMakerId())) {
      filter.setMakerId(filterRequest.getMakerId());
      if (Assert.has(filterRequest.getModelId())) {
        filter.setModelId(filterRequest.getModelId());
      }
    }
    Specification<Offer> offerSpecification = where(OfferSpecifications.getOffersByFilter(filter));
    return offerRepository
        .findAll(offerSpecification, pageable)
        .map(offer -> modelMapper.map(offer, OfferServiceModel.class));
  }
}
