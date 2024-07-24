package shop.project.venver_order.payment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.project.venver_order.exception.ExceptionCode;
import shop.project.venver_order.exception.ExceptionResponse;
import shop.project.venver_order.order.entity.Order;
import shop.project.venver_order.order.repository.OrderRepository;
import shop.project.venver_order.order.service.OrderService;
import shop.project.venver_order.payment.controller.dto.request.PaymentAddRequest;
import shop.project.venver_order.payment.entity.Payment;
import shop.project.venver_order.payment.repository.PaymentRepository;
import shop.project.venver_order.util.PaymentMethod;
import shop.project.venver_order.util.PaymentStatus;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public void addPayment(Long userId, Long orderId,long amount,PaymentMethod paymentMethod) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new ExceptionResponse(ExceptionCode.NOT_FOUND_PRODUCT));

        Payment payment = Payment.builder()
                .userId(userId)
                .order(order)
                .amount(amount)
                .paymentMethod(paymentMethod)
                .paymentStatus(PaymentStatus.PENDING.name())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);
    }

//    @Transactional
//    public Payment processPayment(User user, Order order, Long amount, PaymentMethod paymentMethod) {
//
//        Payment payment = Payment.builder()
//                .user(user)
//                .order(order)
//                .amount(amount)
//                .paymentMethod(paymentMethod)
//                .paymentStatus(PaymentStatus.PENDING.name())
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
//                .build();
//
//        paymentRepository.save(payment);
//
//        payment.paymentStatusUpdate(PaymentStatus.COMPLETED);
//        return paymentRepository.save(payment);
//    }

    @Transactional
    public void updatePaymentStatus(Long paymentId, PaymentStatus newStatus) {
        Payment payment = paymentRepository.findByOrderId(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.paymentStatusUpdate(newStatus);
        paymentRepository.save(payment);
    }
}
