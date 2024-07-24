package shop.project.venver_user.shipping_address.service.dto.req;

import shop.project.venver_user.shipping_address.controller.dto.request.ShippingAddressUpdateControllerRequest;

public record ShippingAddressUpdateServiceRequest (Long addressId, String address, String detailAddress){
    public ShippingAddressUpdateServiceRequest(ShippingAddressUpdateControllerRequest request){
        this(request.addressId(),request.address(),request.detailAddress());
    }
}
