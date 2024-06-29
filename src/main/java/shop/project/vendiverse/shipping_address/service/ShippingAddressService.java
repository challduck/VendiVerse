package shop.project.vendiverse.shipping_address.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.project.vendiverse.exception.ExceptionCode;
import shop.project.vendiverse.exception.ExceptionResponse;
import shop.project.vendiverse.security.UserDetailsImpl;
import shop.project.vendiverse.shipping_address.dto.request.ShippingAddressRequest;
import shop.project.vendiverse.shipping_address.entity.ShippingAddress;
import shop.project.vendiverse.shipping_address.repository.ShippingAddressRepository;
import shop.project.vendiverse.user.entity.User;
import shop.project.vendiverse.user.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingAddressService {

    private final ShippingAddressRepository shippingAddressRepository;
    private final UserService userService;

    @Transactional
    public void addShippingAddr(UserDetailsImpl userDetails, ShippingAddressRequest request){
        String email = userDetails.getUsername();
        User user = userService.findUserByEmail(email);

        List<ShippingAddress> shippingAddresses = shippingAddressRepository.findByUserId(user.getId());

        if (shippingAddresses.isEmpty()){

            ShippingAddress shippingAddress = ShippingAddress.builder()
                    .user(user)
                    .address(request.addr())
                    .detailAddress(request.detailAddr())
                    .isPrimary(request.isPrimary())
                    .build();
            shippingAddressRepository.save(shippingAddress);

        } else {

            for (ShippingAddress existingAddress : shippingAddresses) {
                if (existingAddress.isPrimary()) {
                    existingAddress.updatePrimaryAddress(false);
                    shippingAddressRepository.save(existingAddress);
                }
            }
            ShippingAddress newShippingAddress = ShippingAddress.builder()
                    .user(user)
                    .address(request.addr())
                    .detailAddress(request.detailAddr())
                    .isPrimary(request.isPrimary())
                    .build();
            shippingAddressRepository.save(newShippingAddress);

        }
    }

}
