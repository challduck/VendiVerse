package shop.project.vendiverse.shipping_address.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.project.vendiverse.security.UserDetailsImpl;
import shop.project.vendiverse.shipping_address.dto.request.ShippingAddressRequest;
import shop.project.vendiverse.shipping_address.service.ShippingAddressService;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class ShippingAddressController {

    private final ShippingAddressService shippingAddressService;

    @PostMapping("/add")
    public ResponseEntity<String> addShippingAddr(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ShippingAddressRequest request){

        shippingAddressService.addShippingAddr(userDetails, request);

        return ResponseEntity.ok().body("배송지 추가 완료");
    }
}
