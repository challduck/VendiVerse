package shop.project.venver_user.shipping_address.service.dto.req;

import shop.project.venver_user.shipping_address.controller.dto.request.ShippingAddressRequest;

public record ShippingAddressServiceRequest(String addr, String detailAddr, boolean isPrimary){
    public ShippingAddressServiceRequest(ShippingAddressRequest request){
        this(
                request.addr(),
                request.detailAddr(),
                request.isPrimary()
        );
    }
}
