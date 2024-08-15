package uz.ksan.backend.pizzadelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ksan.backend.pizzadelivery.models.entities.OrderEntity;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findById(Long id);
}