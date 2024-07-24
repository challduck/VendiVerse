package shop.project.venver_user.shipping_address.service.dto.res;

import lombok.Builder;

public record ShippingAddressListResponse(Long addressId, String address, String detailAddress, boolean isPrimary) {
    @Builder
    public ShippingAddressListResponse{

    }
}
