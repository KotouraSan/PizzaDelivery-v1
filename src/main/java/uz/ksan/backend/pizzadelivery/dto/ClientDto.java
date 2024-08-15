package uz.ksan.backend.pizzadelivery.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    Long id;
    String firstName;
    String lastName;
    String username;
    String password;
    Long phoneNumber;
    String role;
    String address;
    String profileImageUrl;
}