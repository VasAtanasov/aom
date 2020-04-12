package bg.autohouse.service.services.impl;

import bg.autohouse.data.models.User;
import bg.autohouse.service.models.PrivateSellerAccountServiceModel;
import bg.autohouse.service.services.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

  @Override
  public void createPrivateSellerAccount(PrivateSellerAccountServiceModel model, User owner) {
    // TODO Auto-generated method stub

  }
  // @Override
  // public DealershipServiceModel registerDealer(String userId,
  // DealershipServiceModel dealer) {

  // if (existsByDealershipName(dealer.getName())) {
  // throw new ResourceAlreadyExistsException(
  // String.format(ExceptionsMessages.DEALERSHIP_ALREADY_EXISTS,
  // dealer.getName()));
  // }
  // Address address =
  // addressRepository
  // .findById(dealer.getLocationId())
  // .orElseThrow(() -> new
  // NotFoundException(ExceptionsMessages.INVALID_LOCATION));
  // User user =
  // userRepository
  // .findById(userId)
  // .orElseThrow(
  // () ->
  // new
  // UsernameNotFoundException(ExceptionsMessages.EXCEPTION_USER_NOT_FOUND_ID));

  // Dealership dealership = modelMapper.map(dealer, Dealership.class);
  // dealership.setDealer(user);
  // dealershipRepository.save(dealership);
  // log.info("Saved dealership with name: {}", dealership.getName());
  // return modelMapper.map(dealership, DealershipServiceModel.class);

  // return null;
  // }
}
