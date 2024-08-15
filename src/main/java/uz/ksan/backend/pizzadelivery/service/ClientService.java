package uz.ksan.backend.pizzadelivery.service;

import uz.ksan.backend.pizzadelivery.dto.ClientDto;
import uz.ksan.backend.pizzadelivery.dto.JwtAuthenticationDto;
import uz.ksan.backend.pizzadelivery.dto.RefreshTokenDto;
import uz.ksan.backend.pizzadelivery.dto.UserCredentialsDto;
import uz.ksan.backend.pizzadelivery.models.entities.ClientEntity;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Optional;

public interface ClientService {

    String addClient(ClientDto client);
    ClientEntity updateClient(ClientEntity client);
    Optional<ClientEntity> displayClient(String username);
    List<ClientEntity> displayAllClients();
    void deleteByPhoneNumber(Long phoneNumber);

    ClientEntity findClientById(Long id);
    ClientEntity findByPhoneNumber(Long phoneNumber);
//    ClientDto getByUserName(String username) throws ChangeSetPersister.NotFoundException;
    Optional<ClientEntity> findByAddress(String address);

    List<ClientEntity> findByFilters(String firstName, String lastName, Long phoneNumber, String address);

    JwtAuthenticationDto signIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException;
    JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception;


    ClientEntity updateProfileImageUrl(String username, String profileImageUrl);
}
