package uz.ksan.backend.pizzadelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class UserCredentialsDto {
    private String username;
    private String password;

}
