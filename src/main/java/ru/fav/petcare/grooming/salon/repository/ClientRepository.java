package ru.fav.petcare.grooming.salon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.fav.petcare.grooming.salon.entity.Client;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    @Query
    Optional<Client> findByPhone(String phone);

    Optional<Client> findById(Long clientId);

    void deleteById(Long clientId);
}