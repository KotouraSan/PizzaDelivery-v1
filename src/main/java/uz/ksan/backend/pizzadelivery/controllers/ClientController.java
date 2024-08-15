package uz.ksan.backend.pizzadelivery.controllers;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import uz.ksan.backend.pizzadelivery.dto.AckDTO;
import uz.ksan.backend.pizzadelivery.dto.ClientDto;
import uz.ksan.backend.pizzadelivery.models.entities.ClientEntity;
import uz.ksan.backend.pizzadelivery.models.entities.OrderEntity;
import uz.ksan.backend.pizzadelivery.security.jwt.JwtService;
import uz.ksan.backend.pizzadelivery.service.ClientService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@Transactional
@RequestMapping("/api/v1/")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientController {

    @Autowired
    ClientService clientService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("client/newClient")
    public String createClient(@RequestBody ClientDto clients) {
        return clientService.addClient(clients);
    }

    @PutMapping("client/updateClient")
    public ClientEntity updateClient(@RequestBody ClientEntity client) {
        return clientService.updateClient(client);
    }

    @DeleteMapping("client/deleteClient/{phoneNumber}")
    public AckDTO deleteByPhoneNumber(@PathVariable("phoneNumber") Long phoneNumber) {
        clientService.deleteByPhoneNumber(phoneNumber);
        return AckDTO.makeDefault(true);
    }

    @GetMapping("client/displayAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<ClientEntity> getAllClients(){
        return clientService.displayAllClients();
    }

    @GetMapping("client/profile")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Optional<ClientEntity>> getClient(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "").trim();
        String username = jwtService.extractUsernameFromToken(token);
        Optional<ClientEntity> client = Optional.ofNullable(clientService.displayClient(username).orElseThrow(() -> new UsernameNotFoundException("Username not found")));
        return ResponseEntity.ok(client);
    }

    @GetMapping("client/find/")
    public List<ClientEntity> findByFilters(@RequestParam (required = false)String firstName,
                                            @RequestParam (required = false)Long phoneNumber,
                                            @RequestParam (required = false)String lastName,
                                            @RequestParam (required = false)String address) {
        return clientService.findByFilters(firstName, lastName, phoneNumber, address);
    }

    @PutMapping("client/{username}/profileImageUrl")
    public ResponseEntity<ClientEntity> updateProfileImageUrl(@PathVariable String username,
                                                              @RequestBody String profileImageUrl) {
        clientService.updateProfileImageUrl(username,profileImageUrl);
        return ResponseEntity.ok().build();
    }

}
