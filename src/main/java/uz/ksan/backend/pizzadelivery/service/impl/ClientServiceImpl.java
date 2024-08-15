package uz.ksan.backend.pizzadelivery.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.ksan.backend.pizzadelivery.dto.ClientDto;
import uz.ksan.backend.pizzadelivery.dto.JwtAuthenticationDto;
import uz.ksan.backend.pizzadelivery.dto.RefreshTokenDto;
import uz.ksan.backend.pizzadelivery.dto.UserCredentialsDto;
import uz.ksan.backend.pizzadelivery.mapper.ClientMapper;
import uz.ksan.backend.pizzadelivery.models.entities.ClientEntity;
import uz.ksan.backend.pizzadelivery.repository.ClientRepository;
import uz.ksan.backend.pizzadelivery.security.jwt.JwtService;


import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
@Primary
@Transactional
public class ClientServiceImpl implements uz.ksan.backend.pizzadelivery.service.ClientService {

    private ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Override
    public String addClient(ClientDto clientDto) {
        ClientEntity client = clientMapper.toEntity(clientDto);
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client.setProfileImageUrl("https://static.vecteezy.com/system/resources/previews/002/318/271/non_2x/user-profile-icon-free-vector.jpg");
        clientRepository.save(client);
        return "User added";
    }

    @Override
    public ClientEntity updateClient(ClientEntity client) {
        return clientRepository.save(client);
    }


    @Override
    public Optional<ClientEntity> displayClient(String username) {
        return clientRepository.findByUsername(username);
    }

    @Override
    public List<ClientEntity> displayAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public ClientEntity findClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client with this id not found"));
    }


    private ClientEntity findByCredentials(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        Optional<ClientEntity> optionalClient = clientRepository.findByUsername(userCredentialsDto.getUsername());
        if (optionalClient.isPresent()) {
            ClientEntity client = optionalClient.get();
            if (passwordEncoder.matches(userCredentialsDto.getPassword(), client.getPassword())) {
                return client;
            }
        }
        throw new AuthenticationException("Username or password is incorrect");
    }

    private ClientEntity findByUserName(String username) throws Exception {
        return clientRepository.findByUsername(username).orElseThrow(()->
                new Exception(String.format("User with username %s not found", username)));
    }

    //    @Override
//    public Optional<ClientEntity> findByFullName(String fullName) {
//        return clientRepository.findByFullName(fullName);
//    }

    @Override
    public ClientEntity findByPhoneNumber(Long phoneNumber) {
        return clientRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new RuntimeException("Client with this Phone not found"));
    }

    @Override
    public Optional<ClientEntity> findByAddress(String address) {
        return clientRepository.findByAddress(address);
    }

    @Override
    public void deleteByPhoneNumber(Long phoneNumber) {
        clientRepository.findByPhoneNumber(phoneNumber)
                .ifPresent(client -> clientRepository
                        .delete(client));
    }

    @Override
    public List<ClientEntity> findByFilters(String firstName, String lastName, Long phoneNumber, String address) {
        return clientRepository.findByFilters(firstName, phoneNumber, lastName,address);
    }


    @Override
    public JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception {
        String refreshToken = refreshTokenDto.getRefreshToken();
        if (refreshToken != null && jwtService.validateJwtToken(refreshToken)) {
            ClientEntity client = findByUserName(jwtService.getUsernameFromToken(refreshToken));
            return jwtService.refreshBaseToken(client.getUsername(), refreshToken);
        }
        throw new AuthenticationException("Invalid refresh token");
    }

    @Override
    public JwtAuthenticationDto signIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        ClientEntity client = findByCredentials(userCredentialsDto);
        return jwtService.generateAuthToken(client.getUsername());
    }

    @Override
    public ClientEntity updateProfileImageUrl(String username, String profileImageUrl) {
        ClientEntity clients = clientRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Client with this username not found"));
        clients.setProfileImageUrl(profileImageUrl);
        return clientRepository.save(clients);
    }
}
