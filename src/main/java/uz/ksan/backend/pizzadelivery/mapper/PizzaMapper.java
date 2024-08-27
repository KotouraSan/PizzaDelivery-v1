package uz.ksan.backend.pizzadelivery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.ksan.backend.pizzadelivery.dto.ClientDto;
import uz.ksan.backend.pizzadelivery.dto.PizzaDto;
import uz.ksan.backend.pizzadelivery.models.entities.ClientEntity;
import uz.ksan.backend.pizzadelivery.models.entities.PizzaEntity;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PizzaMapper {

    PizzaEntity toEntity(PizzaDto pizzaDto);
    PizzaDto toDto(PizzaEntity pizza);
}