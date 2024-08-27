package uz.ksan.backend.pizzadelivery.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.ksan.backend.pizzadelivery.dto.ClientDto;
import uz.ksan.backend.pizzadelivery.exceptions.NotFoundException;
import uz.ksan.backend.pizzadelivery.mapper.ClientMapper;
import uz.ksan.backend.pizzadelivery.models.entities.ClientEntity;
import uz.ksan.backend.pizzadelivery.repository.ClientRepository;
import uz.ksan.backend.pizzadelivery.security.jwt.JwtService;
import uz.ksan.backend.pizzadelivery.service.impl.ClientServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);     //что бы не писать каждый раз Mock.assert и тп
    }

    @Test
    public void ClientServiceImpl_findByUsername_ShouldReturnObjectWithUsername() {
        String username = "testUser";
        ClientEntity client = ClientEntity.builder()
                .username(username)
                .password("password")
                .firstName("john")
                .build();

        String username1 = "exception user";

        when(clientRepository.findByUsername(username)).thenReturn(Optional.of(client));
        Optional<ClientEntity> result = clientService.displayClient(username);

        assertTrue(result.isPresent());
        assertEquals(client, result.get());
        }


    @Test
    public void ClientServiceImpl_findClientById_ShouldReturnObjectWithIdOrThrowException () {
        Long id1 = 1L;
        String username = "user1";
        ClientEntity client1 = ClientEntity.builder()
                .username(username)
                .id(id1)
                .password("pass1")
                .build();

        Long id2 = 2L;
        String username2 = "user2";
        ClientEntity client2 = ClientEntity.builder()
                .username(username2)
                .id(id2)
                .password("pass2")
                .build();

        when(clientRepository.findById(id1)).thenReturn(Optional.of(client1));
        when(clientRepository.findById(id2)).thenReturn(Optional.of(client2));

        ClientEntity res1 = clientService.findClientById(id1);
        ClientEntity res2 = clientService.findClientById(id2);

        assertEquals(res1, client1);
        assertEquals(res2, client2);
    }

    @Test
    public void ClientServiceImpl_addClient_ShouldReturnSuccessMessage() {
    //TODO
        ClientDto clientDto = ClientDto.builder()
                .username("user")
                .password("pass")
                .build();

        ClientEntity client = ClientEntity.builder()
                .username("user")
                .password("encodedpass")
                .profileImageUrl("https://static.vecteezy.com/system/resources/previews/002/318/271/non_2x/user-profile-icon-free-vector.jpg")
                .build();

        when(clientMapper.toEntity(clientDto)).thenReturn(client);
        when(passwordEncoder.encode(clientDto.getPassword())).thenReturn("encodedpass");
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(client);


        String result = clientService.addClient(clientDto);


        assertEquals("User added", result);
        verify(clientRepository, times(1)).save(any(ClientEntity.class));
        assertEquals("encodedpass", client.getPassword());

    }


    @Test
    public void ClientServiceImpl_displayClient_ShouldReturnClient() {
        String username = "user";
        ClientEntity client = ClientEntity.builder()
                .firstName("name")
                .lastName("lastname")
                .username(username)
                .password("pass")
                .build();

        when(clientRepository.findByUsername(username)).thenReturn(Optional.of(client));

        Optional<ClientEntity> res = clientService.displayClient(username);

        assertTrue(res.isPresent());
        assertEquals(client, res.get());
        verify(clientRepository, times(1)).findByUsername(username);
    }

    @Test
    public void ClientServiceImpl_displayAllClients_ShouldReturnAllClients() {
        List<ClientEntity> clients = Arrays.asList(ClientEntity.builder().username("user1").password("pass1").build(),
                                                    ClientEntity.builder().username("user2").password("pass2").build());
        when(clientRepository.findAll()).thenReturn(clients);

        List<ClientEntity> result = clientService.displayAllClients();

        assertEquals(clients, result);
        assertEquals(clients.size(), result.size());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    public void ClientServiceImpl_findByPhoneNumber_ShouldReturnClientByPhoneNumber() {
        List<ClientEntity> clients = Arrays.asList(ClientEntity.builder().username("user1").phoneNumber(1L).password("pass1").build(),
                                                    ClientEntity.builder().username("user2").phoneNumber(2L).password("pass2").build());

        when(clientRepository.findByPhoneNumber(1L)).thenReturn(Optional.of(clients.get(0)));
        when(clientRepository.findByPhoneNumber(2L)).thenReturn(Optional.of(clients.get(1)));

        ClientEntity result1 = clientService.findByPhoneNumber(1L);
        ClientEntity result2 = clientService.findByPhoneNumber(2L);

        assertEquals(clients.get(0), result1);
        assertEquals(clients.get(1), result2);
        verify(clientRepository, times(1)).findByPhoneNumber(1L);
        verify(clientRepository, times(1)).findByPhoneNumber(2L);
    }

    @Test
    public void ClientServiceImpl_findByAddress_ShouldReturnClientByAddress() {
        List<ClientEntity> clients = Arrays.asList(ClientEntity.builder().username("user1").password("pass1").address("first").build(),
                                                    ClientEntity.builder().username("user2").password("pass2").address("second").build());

        when(clientRepository.findByAddress("first")).thenReturn(Optional.of(clients.get(0)));
        when(clientRepository.findByAddress("second")).thenReturn(Optional.of(clients.get(1)));
        ClientEntity result1 = clientService.findByAddress("first").orElse(null);
        ClientEntity result2 = clientService.findByAddress("second").orElse(null);

        assertEquals(clients.get(0), result1);
        assertEquals(clients.get(1), result2);
        verify(clientRepository, times(1)).findByAddress("first");
        verify(clientRepository, times(1)).findByAddress("second");
    }

    @Test
    public void ClientServiceImpl_deleteByPhoneNumber_ShouldDeleteClientByPhoneNumber() {
        Long phoneNumber = 1L;
        ClientEntity client = ClientEntity.builder().username("user1").phoneNumber(1L).password("pass1").build();

        when(clientRepository.findByPhoneNumber(1L)).thenReturn(Optional.of(client));
        clientService.deleteByPhoneNumber(phoneNumber);

        assertEquals(client, clientService.findByPhoneNumber(1L));
        verify(clientRepository, times(1)).delete(client);
    }
}
