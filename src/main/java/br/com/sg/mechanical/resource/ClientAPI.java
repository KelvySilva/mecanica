package br.com.sg.mechanical.resource;


import br.com.sg.mechanical.domain.Client;
import br.com.sg.mechanical.domain.GenericResponse;
import br.com.sg.mechanical.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("v1")
public class ClientAPI {

    private ClientService service;

    @Autowired
    public ClientAPI(ClientService service) {
        this.service = service;
    }

    @GetMapping(path = "/protected/clients")
    public ResponseEntity listAll(){
        List<Client> clients = this.service.listAll();

        if(clients.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(clients);
    }
}
