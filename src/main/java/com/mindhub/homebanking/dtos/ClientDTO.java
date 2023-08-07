package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    //private Set<Account> accounts;
    private Set<AccountDTO> accountsDTO;

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accountsDTO = client.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toSet());
    }
    public long getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public Set<AccountDTO> getAccountsDTO() {
        return this.accountsDTO;
    }

}
