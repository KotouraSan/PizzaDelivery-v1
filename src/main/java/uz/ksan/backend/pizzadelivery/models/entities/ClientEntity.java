package uz.ksan.backend.pizzadelivery.models.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnTransformer;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientEntity {

    @SequenceGenerator(name="yourSequenceGeneratorClient", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="yourSequenceGeneratorClient")
    @Id
    Long id;

    @ColumnTransformer(write = "LOWER(?)")
    String firstName;

    @ColumnTransformer(write = "LOWER(?)")
    String lastName;

    @Column(unique=true)
    Long phoneNumber;

    @ColumnTransformer(write = "LOWER(?)")
    String address;

    String role;

    String username;

    String password;

    String profileImageUrl;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client")
    private List<OrderEntity> orders = new ArrayList<>();


//    public String getFullName() {
//        fullName = firstName + " " + lastName;
//        return fullName;
//    }
//    public void setFullName(String fullName) {
//        this.fullName = fullName;
//    }
}
