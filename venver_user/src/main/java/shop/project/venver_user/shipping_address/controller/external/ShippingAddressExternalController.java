package shop.project.venver_user.shipping_address.controller.external;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.project.venver_user.shipping_address.controller.dto.request.ShippingAddressRequest;
import shop.project.venver_user.shipping_address.controller.dto.request.ShippingAddressUpdateControllerRequest;
import shop.project.venver_user.shipping_address.service.ShippingAddressService;
import shop.project.venver_user.shipping_address.service.dto.req.ShippingAddressServiceRequest;
import shop.project.venver_user.shipping_address.service.dto.req.ShippingAddressUpdateServiceRequest;
import shop.project.venver_user.shipping_address.service.dto.res.ShippingAddressListResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class ShippingAddressExternalController {

    private final ShippingAddressService shippingAddressService;

    @PostMapping("/add")
    public ResponseEntity<String> addShippingAddr(@RequestHeader(name = "userId") Long userId, @RequestBody ShippingAddressRequest request){
        ShippingAddressServiceRequest serviceRequest = new ShippingAddressServiceRequest(request);
        shippingAddressService.addShippingAddr(userId, serviceRequest);

        return ResponseEntity.ok().body("배송지 추가 완료");
    }

    @PatchMapping("/update")
    public ResponseEntity<Void> updateShippingAddr(@RequestHeader(name = "userId") Long userId, @RequestBody ShippingAddressUpdateControllerRequest request){
        ShippingAddressUpdateServiceRequest serviceRequest = new ShippingAddressUpdateServiceRequest(request);
        shippingAddressService.shippingAddrUpdate(userId, serviceRequest);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<ShippingAddressListResponse>> getShippingAddrList(@RequestHeader(name = "userId") Long userId){
        List<ShippingAddressListResponse> responses = shippingAddressService.getShippingAddrList(userId);

        return ResponseEntity.ok().body(responses);
    }
}
