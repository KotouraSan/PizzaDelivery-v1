package uz.ksan.backend.pizzadelivery.service;

import uz.ksan.backend.pizzadelivery.dto.PizzaDto;
import uz.ksan.backend.pizzadelivery.models.entities.PizzaEntity;

public interface PizzaService {
    String addPizza(PizzaDto pizzaDto);
}
