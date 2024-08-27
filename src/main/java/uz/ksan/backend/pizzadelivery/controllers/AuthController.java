package uz.ksan.backend.pizzadelivery.controllers;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import uz.ksan.backend.pizzadelivery.dto.JwtAuthenticationDto;
import uz.ksan.backend.pizzadelivery.dto.RefreshTokenDto;
import uz.ksan.backend.pizzadelivery.dto.UserCredentialsDto;
import uz.ksan.backend.pizzadelivery.service.ClientService;

import javax.naming.AuthenticationException;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    final ClientService clientService;

    @SneakyThrows
    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationDto> signIn(@RequestBody UserCredentialsDto userCredentialsDto) {
        try {
            JwtAuthenticationDto jwtAuthenticationDto = clientService.signIn(userCredentialsDto);
            return ResponseEntity.ok(jwtAuthenticationDto);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Authentication failed" + e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public JwtAuthenticationDto refresh(@RequestBody RefreshTokenDto refreshTokenDto) throws Exception {
        return clientService.refreshToken(refreshTokenDto);
    }

}
