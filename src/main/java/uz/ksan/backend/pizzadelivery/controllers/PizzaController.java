package uz.ksan.backend.pizzadelivery.controllers;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.ksan.backend.pizzadelivery.dto.PizzaDto;
import uz.ksan.backend.pizzadelivery.repository.PizzaRepository;
import uz.ksan.backend.pizzadelivery.service.impl.PizzaServiceImpl;

@RequiredArgsConstructor
@RestController
@Transactional
@RequestMapping("/api/v1/")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PizzaController {

    PizzaServiceImpl pizzaService;

    @PostMapping("pizza/newPizza")
    public String addPizza(@RequestBody PizzaDto pizza) {
        return pizzaService.addPizza(pizza);
    }

}
