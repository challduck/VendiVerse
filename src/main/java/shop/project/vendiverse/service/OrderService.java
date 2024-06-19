package shop.project.vendiverse.service;

import org.springframework.stereotype.Service;
import shop.project.vendiverse.domain.Order;
import shop.project.vendiverse.repository.OrderRepository;
import shop.project.vendiverse.repository.UserRepository;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private Order order;


}
