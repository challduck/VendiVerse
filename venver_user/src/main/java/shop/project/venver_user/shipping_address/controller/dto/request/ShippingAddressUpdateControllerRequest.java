package shop.project.venver_user.shipping_address.controller.dto.request;

public record ShippingAddressUpdateControllerRequest (Long addressId, String address, String detailAddress) {
}
