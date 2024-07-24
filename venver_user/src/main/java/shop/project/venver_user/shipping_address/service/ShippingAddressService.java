package shop.project.venver_user.shipping_address.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.project.venver_user.exception.ExceptionCode;
import shop.project.venver_user.exception.ExceptionResponse;
import shop.project.venver_user.shipping_address.entity.ShippingAddress;
import shop.project.venver_user.shipping_address.repository.ShippingAddressRepository;
import shop.project.venver_user.shipping_address.service.dto.req.ShippingAddressServiceRequest;
import shop.project.venver_user.shipping_address.service.dto.req.ShippingAddressUpdateServiceRequest;
import shop.project.venver_user.shipping_address.service.dto.res.ShippingAddressListResponse;
import shop.project.venver_user.user.entity.User;
import shop.project.venver_user.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ShippingAddressService {

    private final ShippingAddressRepository shippingAddressRepository;
    private final UserService userService;

    public void addShippingAddr(Long userId, ShippingAddressServiceRequest request){
        User user = userService.findUserById(userId);

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

    public void shippingAddrUpdate(Long userId, ShippingAddressUpdateServiceRequest request){

        User user = userService.findUserById(userId);

        ShippingAddress address = shippingAddressRepository.findById(request.addressId())
                .orElseThrow(()-> new ExceptionResponse(ExceptionCode.NOT_FOUND_ADDRESS));

        address.updateAddress(request.address(), request.detailAddress());

        shippingAddressRepository.save(address);
    }

    public List<ShippingAddressListResponse> getShippingAddrList(Long userId) {
        List<ShippingAddress> shippingAddresses = shippingAddressRepository.findByUserId(userId);

        return shippingAddresses.stream()
                .map(address -> ShippingAddressListResponse.builder()
                        .addressId(address.getId())
                        .address(address.getAddress())
                        .detailAddress(address.getDetailAddress())
                        .isPrimary(address.isPrimary())
                        .build())
                .collect(Collectors.toList());
    }
}
