package uz.ksan.backend.pizzadelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ksan.backend.pizzadelivery.models.entities.ClientEntity;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

//    Optional<ClientEntity> findByFullName(String fullName);
    Optional<ClientEntity> findByPhoneNumber(Long phoneNumber);
    Optional<ClientEntity> findByAddress(String address);
    Optional<ClientEntity> findByUsername(String username);
//    Boolean existsByPhoneNumber(List phoneNumber);

    @Query("select param from ClientEntity param where " +
            "(:firstName is null or param.firstName = :firstName) and " +
            "(:phoneNumber is null or param.phoneNumber = :phoneNumber) and " +
            "(:lastName is null or param.lastName = :lastName) and " +
            "(:address is null or param.address = :address) " )
    List<ClientEntity> findByFilters(@Param("firstName") String firstName,
                                     @Param("phoneNumber") Long phoneNumber,
                                     @Param("address") String address,
                                     @Param("lastName") String lastName);

}

