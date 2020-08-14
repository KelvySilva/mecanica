package br.com.sg.mechanical.resource;


import br.com.sg.mechanical.domain.Client;
import br.com.sg.mechanical.service.ClientService;
import br.com.sg.mechanical.utils.MessageUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Api("Cliente API")
@RestController
@RequestMapping("v1")
public class ClientAPI {

    private ClientService service;

    @Autowired
    public ClientAPI(ClientService service) {
        this.service = service;
    }

    @ApiOperation(value = "Lista todos os clientes.", response = Client[].class, responseContainer = "List",consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Nenhum cliente cadastrado."),
            @ApiResponse(code = 200,message = "Listagem de clientes")
    })
    @GetMapping(path = "/protected/clients")
    public ResponseEntity listAll(){
        List<Client> clients = this.service.listAll();
        if (clients.isEmpty()) return ResponseEntity.ok(MessageUtils.createResourceListIsEmptyMessage());
        else return ResponseEntity.ok(clients);
    }

    @ApiOperation(value = "Encontra um cliente pelo seu ID.", response = Client.class,consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Quando não for possível encontrar o cliente informado"),
            @ApiResponse(code = 200,message = "Cliente buscado.")
    })
    @GetMapping(path = "/protected/client/{id}")
    public ResponseEntity findOne(@ApiParam(value = "Id do Cliente", required = true, example = "3") @PathVariable Long id) {
        Optional<Client> one = this.service.findOne(id);
        if (one.isPresent()) return ResponseEntity.ok(one.get());
        else return ResponseEntity.ok(MessageUtils.createResourceEntityNotPresentMessage(id));
    }

    @ApiOperation(value = "Cria um novo cliente.",  response = Client.class,consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Quando algumas das informações necessárias for vazia ou nula."),
            @ApiResponse(code = 200,message = "Cliente criado.")
    })
    @PostMapping(path = "/admin/client")
    public ResponseEntity saveOne(@RequestBody @Valid Client client,@ApiIgnore Errors errors) {
        if (errors.getFieldErrorCount() > 0) return ResponseEntity.ok(MessageUtils.createFieldErrorMessage(errors));
        try {
            return ResponseEntity.ok(this.service.saveOne(client));
        }catch (DataIntegrityViolationException ex) {
            return ResponseEntity.ok(MessageUtils.createDataIntegrityErrorMessage(ex));
        }
    }

    @ApiOperation(value = "Atualiza os dados de um cliente", response = Client.class,consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Quando não for possível encontrar o cliente informado"),
            @ApiResponse(code = 400, message = "Quando os parametros informados estiverem em desacordo;algumas das informações necessárias for vazia ou nula ou o cpf informado já existir."),
            @ApiResponse(code = 200,message = "Cliente atualizado.")

    })
    @PutMapping(path = "/admin/client/{id}")
    public ResponseEntity updateOne(@ApiParam(value = "Id do Cliente", required = true, example = "3") @PathVariable Long id,@ApiParam("Dados do cliente") @RequestBody @Valid Client client, @ApiIgnore Errors errors) {
        if (id != client.getId()) return ResponseEntity.
                ok(MessageUtils.createRequestConflictErrorMessage());
        if (errors.getFieldErrorCount() > 0) return ResponseEntity.ok(MessageUtils.createFieldErrorMessage(errors));
        if (!this.service.findOne(id).isPresent()) return ResponseEntity.ok(MessageUtils.createResourceEntityNotPresentMessage(id));
        else return ResponseEntity.ok(this.service.updateOne(id,client));
    }

    @ApiOperation(value = "Remove um cliente.",consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Quando não for possível encontrar o cliente informado"),
            @ApiResponse(code = 200, message = "Cliente deletado.")
    })
    @DeleteMapping(path = "/admin/client/{id}")
    public ResponseEntity deleteOne(@ApiParam(value = "Id do Cliente", required = true, example = "3")  @PathVariable Long id) {
        if (!this.service.findOne(id).isPresent()) return ResponseEntity.ok(MessageUtils.createResourceEntityNotPresentMessage(id));
        this.service.deleteOne(id);
        return ResponseEntity.ok(MessageUtils.createDeleteSuccessMessage("Cliente"));
    }
}
