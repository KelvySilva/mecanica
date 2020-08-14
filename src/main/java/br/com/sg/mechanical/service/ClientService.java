package br.com.sg.mechanical.service;

import br.com.sg.mechanical.domain.Client;
import br.com.sg.mechanical.error.ResourceNotFoundException;
import br.com.sg.mechanical.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private ClientRepository repository;

    @Autowired
    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }


    public Optional<Client> findOne(Long id) {
        return this.repository.findById(id);
    }

    public List<Client> listAll() {
        return this.repository.findAll();
    }

    @Transactional
    public Client saveOne(Client client) {
        return this.repository.save(client);
    }

    @Transactional
    public Client updateOne(Long id, Client client) {
        Client clientToUpdate = this.repository.findById(id).get();

        if (clientToUpdate.getName().equalsIgnoreCase(client.getName())){
            clientToUpdate.setName(client.getName());
        }
        if (clientToUpdate.getState().equalsIgnoreCase(client.getState())){
            clientToUpdate.setState(client.getState());
        }
        if (clientToUpdate.getCpf().equalsIgnoreCase(client.getCpf())){
            clientToUpdate.setCpf(client.getCpf());
        }
        if (clientToUpdate.getCity().equalsIgnoreCase(client.getCity())){
            clientToUpdate.setCity(client.getCity());
        }
        if (clientToUpdate.getAddress().equalsIgnoreCase(client.getAddress())){
            clientToUpdate.setAddress(client.getAddress());
        }
        if (clientToUpdate.getAddressNumber().equalsIgnoreCase(client.getAddressNumber())){
            clientToUpdate.setAddressNumber(client.getAddressNumber());
        }
        return this.repository.saveAndFlush(clientToUpdate);
    }

    public Boolean exists(Long id) {
        return this.repository.existsById(id);
    }

    @Transactional
    public void deleteOne(Long id) {
        this.repository.deleteById(id);
    }
}
