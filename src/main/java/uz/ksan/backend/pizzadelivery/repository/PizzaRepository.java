package uz.ksan.backend.pizzadelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ksan.backend.pizzadelivery.models.entities.PizzaEntity;

import java.util.List;

public interface PizzaRepository extends JpaRepository<PizzaEntity, Long> {
    List<PizzaEntity> findByNameIn(List<String> names);
}
