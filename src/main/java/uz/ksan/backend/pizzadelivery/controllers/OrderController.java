package uz.ksan.backend.pizzadelivery.controllers;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.ksan.backend.pizzadelivery.dto.AckDTO;
import uz.ksan.backend.pizzadelivery.models.entities.OrderEntity;
import uz.ksan.backend.pizzadelivery.security.jwt.JwtService;
import uz.ksan.backend.pizzadelivery.service.impl.OrderServiceImpl;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Transactional
@RequestMapping("/api/v1/orders")
@EnableCaching
public class OrderController {

    private final OrderServiceImpl orderService;
    private final JwtService jwtService;

    @PostMapping("/create")
    public ResponseEntity<OrderEntity> createOrder(@RequestHeader("Authorization") String authHeader,
                                                   @RequestParam List<String> pizzaNames) {

        String token = authHeader.replace("Bearer ", "").trim();    //в ответе имеется bearer: его надо вырезать
        String username = jwtService.extractUsernameFromToken(token);
        OrderEntity order = orderService.createOrder(username, pizzaNames);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("remove/{orderId}")
    public ResponseEntity<AckDTO> removeOrder(@RequestHeader("Authorization") String authHeader,
                                              @PathVariable Long orderId) {
        String token = authHeader.replace("Bearer ", "").trim();    //в ответе имеется bearer: его надо вырезать
        String username = jwtService.extractUsernameFromToken(token);
        OrderEntity order = orderService.deleteOrderById(username, orderId);
        return ResponseEntity.ok(AckDTO.makeDefault(true));
    }

}
