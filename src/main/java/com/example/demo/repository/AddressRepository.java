package com.example.demo.repository;

import com.example.demo.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Query(value = "SELECT * FROM Addresses WHERE postalcode = :postalCode", nativeQuery = true)
    List<Address> findByPostalCode(String postalCode);
}
