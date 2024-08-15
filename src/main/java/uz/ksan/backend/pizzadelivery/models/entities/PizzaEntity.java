package uz.ksan.backend.pizzadelivery.models.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pizza")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PizzaEntity {

    @Id
    @SequenceGenerator(name="yourSequenceGeneratorPizza", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="yourSequenceGeneratorPizza")
    Long id;

    String name;

    String description;

    double price;

}