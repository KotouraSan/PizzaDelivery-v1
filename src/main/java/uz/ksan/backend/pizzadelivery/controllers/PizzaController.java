package uz.ksan.backend.pizzadelivery.controllers;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;
import uz.ksan.backend.pizzadelivery.dto.PizzaDto;
import uz.ksan.backend.pizzadelivery.models.entities.PizzaEntity;
import uz.ksan.backend.pizzadelivery.repository.PizzaRepository;
import uz.ksan.backend.pizzadelivery.service.impl.PizzaServiceImpl;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Transactional
@RequestMapping("/api/v1/pizza")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableCaching
public class PizzaController {

    PizzaServiceImpl pizzaService;

    @PostMapping("/newPizza")
    public String addPizza(@RequestBody PizzaDto pizza) {
        return pizzaService.addPizza(pizza);
    }

    @GetMapping("/")
    public List<PizzaEntity> getAllPizzas() {
        return pizzaService.displayPizza();
    }
}
