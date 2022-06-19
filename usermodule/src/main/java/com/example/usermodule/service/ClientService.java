package com.example.usermodule.service;

import com.example.usermodule.entity.Client;

public interface ClientService {
    Client checkLogin(String phone, String password);

    Client save(Client client);
}
