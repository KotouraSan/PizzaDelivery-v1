package uz.ksan.backend.pizzadelivery.service;

import uz.ksan.backend.pizzadelivery.models.entities.OrderEntity;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    OrderEntity createOrder(String username, List<String> pizzaNames);
    Optional<OrderEntity> findOrderById(Long orderId);

}
