package bg.autohouse.service.services.impl;

import static org.springframework.data.jpa.domain.Specification.where;

import bg.autohouse.data.models.Filter;
import bg.autohouse.data.models.Maker;
import bg.autohouse.data.models.Model;
import bg.autohouse.data.models.Offer;
import bg.autohouse.data.repositories.MakerRepository;
import bg.autohouse.data.repositories.OfferRepository;
import bg.autohouse.data.specifications.OfferSpecifications;
import bg.autohouse.service.models.OfferServiceModel;
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
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OfferServiceImpl implements OfferService {

  private final OfferRepository offerRepository;
  private final MakerRepository makerRepository;
  private final ModelMapperWrapper modelMapper;

  @Override
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

  @Override
  public Page<OfferServiceModel> searchOffers(FilterRequest filterRequest, Pageable pageable) {
    Objects.requireNonNull(filterRequest);

    Filter filter = modelMapper.map(filterRequest, Filter.class);

    if (Assert.has(filterRequest.getMakerId())) {
      Maker maker = makerRepository.findById(filterRequest.getMakerId()).orElse(null);
      filter.setMaker(maker);

      if (Assert.has(filterRequest.getModelId())) {
        Model model =
            maker.getModels().stream()
                .filter(m -> m.getId().equals(filterRequest.getModelId()))
                .findFirst()
                .orElse(null);
        filter.setModel(model);
      }
    }

    Specification<Offer> offerSpecification = where(OfferSpecifications.getOffersByFilter(filter));

    return offerRepository
        .findAll(offerSpecification, pageable)
        .map(offer -> modelMapper.map(offer, OfferServiceModel.class));
  }
}
