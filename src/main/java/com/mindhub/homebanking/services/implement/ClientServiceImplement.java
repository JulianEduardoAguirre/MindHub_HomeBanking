package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientServiceImplement implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public List<ClientDTO> getClientsDTO() {
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClient(Long id) {
        return null;
    }

    @Override
    public Optional<Client> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Client createClient(String firstName, String lastName, String email, String password) {
        return null;
    }

//    @Override
//    public Client createClient(String firstName, String lastName, String email, String password) throws Exception {
//        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
//            throw new Exception("Fields can't be null");
//        }
//
//        if (clientRepository.findByEmail(email) != null) {
//            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
//        }
//
//        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
//    }
//
//
//
//
//    @Override
//    public ClientDTO getClient(Long id) {
//        Optional<Client> client = clientRepository.findById(id);
//        return client.map(ClientDTO::new).orElse(null);
//    }
//
//    @Override
//    public Optional<Client> findById(Long id) {
//        Optional<Client> client = clientRepository.findById(id);
//        return client;
//    }


}
