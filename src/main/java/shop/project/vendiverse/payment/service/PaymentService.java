package shop.project.vendiverse.payment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.project.vendiverse.exception.ExceptionCode;
import shop.project.vendiverse.exception.ExceptionResponse;
import shop.project.vendiverse.order.entity.Order;
import shop.project.vendiverse.order.repository.OrderRepository;
import shop.project.vendiverse.order.service.OrderService;
import shop.project.vendiverse.payment.dto.request.PaymentAddRequest;
import shop.project.vendiverse.payment.entity.Payment;
import shop.project.vendiverse.payment.repository.PaymentRepository;
import shop.project.vendiverse.security.UserDetailsImpl;
import shop.project.vendiverse.user.entity.User;
import shop.project.vendiverse.user.service.UserService;
import shop.project.vendiverse.util.PaymentMethod;
import shop.project.vendiverse.util.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
//    private final OrderService orderService;
    private final UserService userService;
    private final OrderRepository orderRepository;

    @Transactional
    public void addPayment(PaymentAddRequest request) {
//        Order order = orderService.getOrderById(request.orderId());
        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(()-> new ExceptionResponse(ExceptionCode.NOT_FOUND_PRODUCT));
        User user = userService.findUserByEmail(request.email());
        Payment payment = Payment.builder()
                .user(user)
                .order(order)
                .amount(request.amount())
                .paymentMethod(request.paymentMethod())
                .paymentStatus(PaymentStatus.PENDING.name())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);
    }

    @Transactional
    public Payment processPayment(User user, Order order, Long amount, PaymentMethod paymentMethod) {

        Payment payment = Payment.builder()
                .user(user)
                .order(order)
                .amount(amount)
                .paymentMethod(paymentMethod)
                .paymentStatus(PaymentStatus.PENDING.name())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);

        payment.paymentStatusUpdate(PaymentStatus.COMPLETED);
        return paymentRepository.save(payment);
    }

    public void updatePaymentStatus(Long paymentId, PaymentStatus newStatus) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.paymentStatusUpdate(newStatus);
        paymentRepository.save(payment);
    }
}
