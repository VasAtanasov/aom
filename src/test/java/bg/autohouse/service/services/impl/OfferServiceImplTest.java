package bg.autohouse.service.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import bg.autohouse.SingletonModelMapper;
import bg.autohouse.data.models.Filter;
import bg.autohouse.data.models.account.Account;
import bg.autohouse.data.models.enums.Feature;
import bg.autohouse.data.models.media.MediaFile;
import bg.autohouse.data.models.offer.Offer;
import bg.autohouse.data.repositories.AccountRepository;
import bg.autohouse.data.repositories.FilterRepository;
import bg.autohouse.data.repositories.LocationRepository;
import bg.autohouse.data.repositories.ModelRepository;
import bg.autohouse.data.repositories.OfferRepository;
import bg.autohouse.data.repositories.VehicleRepository;
import bg.autohouse.service.models.offer.OfferServiceModel;
import bg.autohouse.service.services.MediaFileService;
import bg.autohouse.util.ImageResizer;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.request.offer.OfferCreateRequest;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
public class OfferServiceImplTest {

  @InjectMocks OfferServiceImpl offerService;

  @Mock OfferRepository offerRepository;
  @Mock FilterRepository filterRepository;
  @Mock VehicleRepository vehicleRepository;
  @Mock ModelRepository modelRepository;
  @Mock ModelMapperWrapper modelMapper;
  @Mock LocationRepository locationRepository;
  @Mock AccountRepository accountRepository;
  @Mock MediaFileService mediaFileService;
  @Mock ImageResizer imageResizer;

  Sort sort = Sort.by("createdAt").descending();
  Pageable pageable = PageRequest.of(0, 20, sort);

  @Test
  void whenSearchOffers_noMatchForFeature_shouldReturnEmptyPage() {
    Filter filter = new Filter();
    filter.getFeatures().add(Feature.ABS);
    when(filterRepository.findFilterById(any(UUID.class))).thenReturn(Optional.of(filter));
    when(offerRepository.searchOffersIdsWithFeatures(any(UUID.class)))
        .thenReturn(Collections.emptyList());
    Page<OfferServiceModel> offersPage = offerService.searchOffers(UUID.randomUUID(), pageable);
    assertThat(offersPage.isEmpty()).isTrue();
  }

  @Test
  void whenSearchOffers_withMatchForFeature_shouldReturnNonEmptyPage() {
    Filter filter = new Filter();
    filter.getFeatures().add(Feature.ABS);
    when(filterRepository.findFilterById(any(UUID.class))).thenReturn(Optional.of(filter));
    Offer offer = new Offer();
    offer.setId(UUID.randomUUID());
    when(offerRepository.searchOffersIdsWithFeatures(any(UUID.class))).thenReturn(List.of(offer));
    Specification<Offer> offerSpecification = any();
    when(offerRepository.findAll(offerSpecification, any(Pageable.class)))
        .thenReturn(new PageImpl<>(List.of(offer)));
    when(modelMapper.map(any(Offer.class), eq(OfferServiceModel.class)))
        .thenAnswer(
            invocationOnMock ->
                SingletonModelMapper.mapper()
                    .map(invocationOnMock.getArguments()[0], OfferServiceModel.class));
    Page<OfferServiceModel> offersPage = offerService.searchOffers(UUID.randomUUID(), pageable);
    assertThat(offersPage.isEmpty()).isFalse();
  }

  @Test
  void when_createOffer_maxOfferCountReached_shouldThrow() {
    Account account = new Account();
    account.setId(UUID.randomUUID());
    account.setMaxOffersCount(10);
    UUID userId = UUID.randomUUID();
    when(accountRepository.findByUserId(userId)).thenReturn(Optional.of(account));
    when(offerRepository.countByAccountId(account.getId())).thenReturn(10L);
    Throwable thrown =
        catchThrowable(() -> offerService.createOffer(new OfferCreateRequest(), userId));
    assertThat(thrown)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith(RestMessage.MAX_OFFER_COUNT_REACHED.name());
  }

  @Test
  void when_fetchOfferImages_validId_shouldReturn200() {
    Offer offer = new Offer();
    offer.setId(UUID.randomUUID());
    assertThat(offer.getId()).isNotNull();
    when(offerRepository.existsById(offer.getId())).thenReturn(true);
    MediaFile mediaFile1 = new MediaFile();
    mediaFile1.setFileKey("key1");
    MediaFile mediaFile2 = new MediaFile();
    mediaFile2.setFileKey("key2");
    List<MediaFile> mediaFiles = Arrays.asList(mediaFile1, mediaFile2);
    when(mediaFileService.loadForReference(offer.getId())).thenReturn(mediaFiles);
    List<String> imagesKeys = offerService.fetchOfferImages(offer.getId());
    assertThat(imagesKeys).hasSize(2);
  }
}
