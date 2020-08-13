package br.com.sg.mechanical.resource;


import br.com.sg.mechanical.domain.Client;
import br.com.sg.mechanical.utils.ErrorUtils;
import br.com.sg.mechanical.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        if (clients.isEmpty()) return ResponseEntity.ok(ErrorUtils.createResourceListIsEmptyMessage());
        else return ResponseEntity.ok(clients);
    }

    @GetMapping(path = "/protected/client/{id}")
    public ResponseEntity findOne(@PathVariable Long id) {
        Optional<Client> one = this.service.findOne(id);
        if (one.isPresent()) return ResponseEntity.ok(one.get());
        else return ResponseEntity.ok(ErrorUtils.createResourceEntityNotPresentMessage(id));
    }

    @PostMapping(path = "/admin/client")
    public ResponseEntity saveOne(@RequestBody @Valid Client client) {
        Client clientStored = this.service.saveOne(client);
        if (clientStored instanceof Client) return ResponseEntity.ok(clientStored);
        else return ResponseEntity.ok(ErrorUtils.createResourceListIsEmptyMessage());
    }

    @PutMapping(path = "/admin/client/{id}")
    public ResponseEntity saveOne(@PathVariable Long id,@RequestBody @Valid Client client) {
        if (id != client.getId()) return ResponseEntity.
                ok(ErrorUtils.createRequestConflictErrorMessage());
        Client clientStored = this.service.saveOne(client);
        if (Objects.nonNull(client) && clientStored instanceof Client) return ResponseEntity.ok(clientStored);
        else return ResponseEntity.ok(ErrorUtils.createResourceEntityNotPresentMessage(id));
    }
}
