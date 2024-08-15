package uz.ksan.backend.pizzadelivery.dto;

import lombok.*;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PizzaDto {
    Long id;
    String name;
    String description;
    double price;
}
