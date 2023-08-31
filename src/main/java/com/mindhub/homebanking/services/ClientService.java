package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

void saveClient(Client client);

List<ClientDTO> getClientsDTO();

ClientDTO getClient(Long id);

Optional<Client> findById(Long id);

Client createClient(String firstName,
                    String lastName,
                    String email,
                    String password);

}
