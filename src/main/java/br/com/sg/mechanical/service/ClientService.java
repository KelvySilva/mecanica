package br.com.sg.mechanical.service;

import br.com.sg.mechanical.domain.Client;
import br.com.sg.mechanical.error.ResourceNotFoundException;
import br.com.sg.mechanical.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private ClientRepository repository;

    @Autowired
    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }


//    public List<Client> listAll() {
//        List<Client> all = this.repository.findAll();
//        if (all.isEmpty()) throw new ResourceNotFoundException("Nenhum item cadastrado.");
//        return all;
//    }

//    public Client findOne(Long id) {
//        return this.repository.findById(id)
//                .orElseThrow(
//                        () -> new ResourceNotFoundException(String.format("Nenhum cliente cadastrado para o ID: %s", id))
//                );
//    }
    public Optional<Client> findOne(Long id) {
        return this.repository.findById(id);
    }

    public List<Client> listAll() {
        List<Client> all = this.repository.findAll();
        return all;
    }

    @Transactional
    public Client saveOne(Client client) {
        return this.repository.save(client);
    }

    @Transactional
    public Client updateOne(Long id, Client client) {
        return this.repository
                .findById(id)
                .isPresent() ?
                this.repository.saveAndFlush(client) :
                client;
    }
    public Boolean exists(Long id) {
        return this.repository.existsById(id);
    }
}
