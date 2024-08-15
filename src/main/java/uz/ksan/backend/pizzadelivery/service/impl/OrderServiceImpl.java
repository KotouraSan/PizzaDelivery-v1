package uz.ksan.backend.pizzadelivery.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.ksan.backend.pizzadelivery.models.entities.ClientEntity;
import uz.ksan.backend.pizzadelivery.models.entities.OrderEntity;
import uz.ksan.backend.pizzadelivery.models.entities.PizzaEntity;
import uz.ksan.backend.pizzadelivery.repository.ClientRepository;
import uz.ksan.backend.pizzadelivery.repository.OrderRepository;
import uz.ksan.backend.pizzadelivery.service.ClientService;
import uz.ksan.backend.pizzadelivery.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class OrderServiceImpl implements uz.ksan.backend.pizzadelivery.service.OrderService {

    private final ClientRepository clientRepository;
    private final PizzaServiceImpl pizzaService;
    private final OrderRepository orderRepository;

    public OrderEntity createOrder(String username, List<String> pizzaNames) {
        //получаем клиента по имени пользователя
        ClientEntity client = clientRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Username not found"));


        List<PizzaEntity> pizzasToAdd = pizzaService.getPizzasByName(pizzaNames);       //получение информации о пицце по названию


        OrderEntity order;                         //создание нового заказа или получение существующего
        if (client.getOrders().isEmpty()) {
            order = new OrderEntity();
            order.setPizzas(new ArrayList<>());
            order.setClient(client);
        } else {
            order = client.getOrders().get(0);
        }


        order.getPizzas().addAll(pizzasToAdd);      //добавление в список

        double totalPrice = order.getPizzas().stream().mapToDouble(PizzaEntity::getPrice).sum();
        order.setTotalPrice(totalPrice);

        return orderRepository.save(order);
    }

    @Override
    public Optional<OrderEntity> findOrderById(Long id) {
        orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return orderRepository.findById(id);
    }


}
