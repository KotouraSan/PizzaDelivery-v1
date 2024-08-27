package uz.ksan.backend.pizzadelivery.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uz.ksan.backend.pizzadelivery.models.entities.ClientEntity;

import java.util.List;
import java.util.Optional;

@DataJpaTest        //что бы тесты вообще работали
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2) // проверка будет работать с бд из h2
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void ClientRepository_findByPhoneNumber_ReturnClientWithPhoneNumber() {

        //Arrange
        ClientEntity clientEntity = ClientEntity.builder()
                .firstName("John")
                .lastName("Doe")
                .username("username")
                .password("password")
                .phoneNumber(1233332121L)
                .address("address")
                .build();

        //Act
        clientRepository.save(clientEntity);
        Optional<ClientEntity> clientEntityOptional = clientRepository.findByPhoneNumber(clientEntity.getPhoneNumber());

        //Assert
        assert clientEntityOptional.isPresent();
        assert clientEntityOptional.get().getPhoneNumber().equals(clientEntity.getPhoneNumber());

    }

    @Test
    public void ClientRepository_findByAddress_ReturnClientWithAddress() {

        //Arrange
        ClientEntity clientEntity = ClientEntity.builder()
                .firstName("John")
                .lastName("Doe")
                .username("username")
                .password("password")
                .phoneNumber(1233332121L)
                .address("address")
                .build();

        //act
        clientRepository.save(clientEntity);
        Optional<ClientEntity> clientEntityOptional = clientRepository.findByAddress(clientEntity.getAddress());

        //assert
        assert clientEntityOptional.isPresent();
        assert clientEntityOptional.get().getAddress().equals(clientEntity.getAddress());
    }

    @Test
    public void ClientRepository_findByUsername_ReturnClientWithUsername() {
        ClientEntity clientEntity = ClientEntity.builder()
                .firstName("John")
                .lastName("Doe")
                .username("username")
                .password("password")
                .phoneNumber(1233332121L)
                .address("address")
                .build();

        clientRepository.save(clientEntity);
        Optional<ClientEntity> clientEntityOptional = clientRepository.findByUsername(clientEntity.getUsername());

        assert clientEntityOptional.isPresent();
        assert clientEntityOptional.get().getUsername().equals(clientEntity.getUsername());
    }

    @Test
    public void ClientRepository_findByFilters_ReturnClientWithFilteredValues() {

        ClientEntity clientEntity = ClientEntity.builder()
                .firstName("john")
                .lastName("doe")
                .username("username")
                .password("password")
                .phoneNumber(1233332121L)
                .address("address")
                .build();

        ClientEntity clientEntity2 = ClientEntity.builder()
                .firstName("mary")
                .lastName("doe")
                .username("username1")
                .password("password1")
                .phoneNumber(123L)
                .address("address1")
                .build();

        clientRepository.save(clientEntity);
        clientRepository.save(clientEntity2);
        List<ClientEntity> clientEntityList = clientRepository.findByFilters("john",1233332121L,"address","doe");
        List<ClientEntity> clientEntityList2 = clientRepository.findByFilters("mary",null,null,null);


        assert clientEntityList.getFirst().getUsername().equals(clientEntity.getUsername());
        assert clientEntityList2.getFirst().getFirstName().equals(clientEntity2.getFirstName());

    }
}
