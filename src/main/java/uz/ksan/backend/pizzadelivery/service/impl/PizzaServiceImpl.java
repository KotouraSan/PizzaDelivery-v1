package uz.ksan.backend.pizzadelivery.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import uz.ksan.backend.pizzadelivery.dto.PizzaDto;
import uz.ksan.backend.pizzadelivery.mapper.PizzaMapper;
import uz.ksan.backend.pizzadelivery.models.entities.PizzaEntity;
import uz.ksan.backend.pizzadelivery.repository.PizzaRepository;
import uz.ksan.backend.pizzadelivery.service.PizzaService;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
@Transactional
public class PizzaServiceImpl implements PizzaService {

    private final PizzaRepository pizzaRepository;
    private final PizzaMapper pizzaMapper;

    public List<PizzaEntity> getPizzasByName(List<String> pizzaNames) {
        return pizzaRepository.findByNameIn(pizzaNames);
    }

    @Override
    public String addPizza(PizzaDto pizzaDto) {
        PizzaEntity pizza = pizzaMapper.toEntity(pizzaDto);
        pizzaRepository.save(pizza);
        return "Pizza added";
    }

    @Override
    public List<PizzaEntity> displayPizza() {
        return pizzaRepository.findAll();
    }
}