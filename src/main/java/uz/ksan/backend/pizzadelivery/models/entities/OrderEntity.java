package uz.ksan.backend.pizzadelivery.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderEntity implements Serializable {

    @SequenceGenerator(name="yourSequenceGeneratorOrder", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="yourSequenceGeneratorOrder")
    @Id
    Long id;

    @JsonIgnore  // Игнорируем чтобы избежать цикла
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    ClientEntity client;

    @ManyToMany(fetch = FetchType.EAGER) // EAGER подгружает все необходимые данные из других бд
    @JoinTable(name = "order_pizzas",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "pizza_id"))
    List<PizzaEntity> pizzas;

    double totalPrice;
}
