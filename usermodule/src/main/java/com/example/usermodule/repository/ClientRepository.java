package com.example.usermodule.repository;

import com.example.usermodule.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query(value = "select * from Client where phone = ?1 and password = ?2", nativeQuery = true)
    Client checkLogin(String phone, String password);

    @Query(value = "select * from Client where phone = ?1", nativeQuery = true)
    Client findByPhone(String phone);
}
