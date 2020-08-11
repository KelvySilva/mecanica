package br.com.sg.mechanical.resource;


import br.com.sg.mechanical.domain.Client;
import br.com.sg.mechanical.error.ErrorDetails;
import br.com.sg.mechanical.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        if (clients.isEmpty()) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok(clients);
    }

    @GetMapping(path = "/protected/client/{id}")
    public ResponseEntity findOne(@PathVariable Long id) {
        Optional<Client> one = this.service.findOne(id);
        if (one.isPresent()) return ResponseEntity.ok(one.get());
        else return ResponseEntity.notFound().build();
    }

    @PostMapping(path = "/admin/client")
    public ResponseEntity saveOne(@RequestBody @Valid Client client) {
        Client clientStored = this.service.saveOne(client);
        if (clientStored instanceof Client) return ResponseEntity.ok(clientStored);
        else return ResponseEntity.ok(
                ErrorDetails.Builder.anErrorDetails()
                        .title("Nenhum cliente encontrado!")
                        .status(404)
                        .timestamp(Date.from(Instant.now()).getTime())
                        .detail(String.format("Nenhum cliente encontrado"))
                        .developerMessage("DEVELOPMENT")
                        .build()
        );
    }

    @PutMapping(path = "/admin/client/{id}")
    public ResponseEntity saveOne(@PathVariable Long id,@RequestBody @Valid Client client) {
        if (id != client.getId()) return ResponseEntity.
                ok(
                    ErrorDetails.Builder.anErrorDetails()
                    .title("Informações divergentes!")
                    .status(404)
                    .timestamp(Date.from(Instant.now()).getTime())
                    .detail(String.format("O Id informado na URL está diferente do id infromado no corppo."))
                    .developerMessage("")
                    .build()
                );
        Client clientStored = this.service.saveOne(client);
        if (Objects.nonNull(client) && clientStored instanceof Client) return ResponseEntity.ok(clientStored);
        else return ResponseEntity.ok(
                ErrorDetails.Builder.anErrorDetails()
                        .title("Nenhum client encontrado")
                        .status(404)
                        .timestamp(Date.from(Instant.now()).getTime())
                        .detail(String.format("Nenhum client encontrado para o ID: ", id))
                        .developerMessage("")
                        .build()
        );
    }
}
