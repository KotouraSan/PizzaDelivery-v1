package uz.ksan.backend.pizzadelivery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.ksan.backend.pizzadelivery.dto.ClientDto;
import uz.ksan.backend.pizzadelivery.models.entities.ClientEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {
    @Mapping(source = "password", target = "password")
    ClientEntity toEntity(ClientDto clientDto);
    ClientDto toDto(ClientEntity client);
}

