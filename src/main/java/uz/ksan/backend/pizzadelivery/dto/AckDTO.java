package uz.ksan.backend.pizzadelivery.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AckDTO {
    boolean answer;

    public static AckDTO makeDefault(boolean answer) {
        return builder()
                .answer(answer)
                .build();

    }         //DTO for deleting users
}
