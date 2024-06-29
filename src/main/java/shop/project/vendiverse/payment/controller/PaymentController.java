package shop.project.vendiverse.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.project.vendiverse.payment.dto.request.PaymentAddRequest;
import shop.project.vendiverse.payment.service.PaymentService;
import shop.project.vendiverse.security.UserDetailsImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

//    @PostMapping("/add")
//    public ResponseEntity<Void> addPayment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PaymentAddRequest request) {
//        paymentService.addPayment(userDetails, request);
//        return ResponseEntity.ok().build();
//
//    }
//
//    @PutMapping("/update")
//    public ResponseEntity<Void> updatePayment(){
//
//        return ResponseEntity.ok().build();
//    }
}
