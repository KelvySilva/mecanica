package br.com.sg.mechanical.service;

import br.com.sg.mechanical.domain.Client;
import br.com.sg.mechanical.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private ClientRepository repository;

    @Autowired
    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }


    public List<Client> listAll() {
        return this.repository.findAll();
    }
}
