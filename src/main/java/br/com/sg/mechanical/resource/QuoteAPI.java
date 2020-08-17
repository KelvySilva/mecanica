package br.com.sg.mechanical.resource;

import br.com.sg.mechanical.domain.Client;
import br.com.sg.mechanical.domain.Quote;
import br.com.sg.mechanical.utils.MessageUtils;
import br.com.sg.mechanical.service.QuoteService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Api("Quote API")
@RestController
@RequestMapping("v1")
public class QuoteAPI {

    private QuoteService service;

    @Autowired
    public QuoteAPI(QuoteService service) {
        this.service = service;
    }

    @ApiOperation(value = "Lista todos os orçamentos.", response = Quote[].class, responseContainer = "List",consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Nenhum orçamento cadastrado."),
            @ApiResponse(code = 200,message = "Listagem de orçamentos")
    })
    @GetMapping(path = "/protected/quotes")
    public ResponseEntity listAll() {
        List<Quote> quotes = this.service.listAll();
        if (quotes.isEmpty()) return ResponseEntity.ok(MessageUtils.createResourceListIsEmptyMessage());
        else return ResponseEntity.ok(quotes);
    }

    @ApiOperation(value = "Lista todos os orçamentos pelo id do cliente.", response = Quote[].class, responseContainer = "List",consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Nenhum orçamento cadastrado."),
            @ApiResponse(code = 200,message = "Listagem de orçamentos")
    })
    @GetMapping(path = "/protected/quotes/client/{id}")
    public ResponseEntity listAllByClientId(@ApiParam(value = "Id do cliente", required = true, example = "3") @PathVariable Long id) {
        List<Quote> quotes = this.service.findAllByClientId(id);
        if (quotes.isEmpty()) return ResponseEntity.ok(MessageUtils.createResourceListIsEmptyMessage());
        else return ResponseEntity.ok(quotes);
    }

    @ApiOperation(value = "Lista todos os orçamentos pelo cpf do cliente (pode usar ponto).", response = Quote[].class, responseContainer = "List",consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Nenhum orçamento cadastrado."),
            @ApiResponse(code = 200,message = "Listagem de orçamentos")
    })
    @GetMapping(path = "/protected/quotes/client/cpf/{cpf}")
    public ResponseEntity listAllByClientCpf(@PathVariable("cpf") String cpf) {
        List<Quote> quotes = this.service.findAllByClientCpf(cpf);
        if (quotes.isEmpty()) return ResponseEntity.ok(MessageUtils.createResourceListIsEmptyMessage());
        else return ResponseEntity.ok(quotes);
    }

    @ApiOperation(value = "Lista todos os orçamentos que o nome do cliente comece com as letras ou conjunto de letras informados.", response = Quote[].class, responseContainer = "List",consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Nenhum orçamento cadastrado."),
            @ApiResponse(code = 200,message = "Listagem de orçamentos")
    })
    @GetMapping(path = "/protected/quotes/client")
    public ResponseEntity listAllByClientNameStartsWith(@RequestParam String name) {
        List<Quote> quotes = this.service.findAllByClientNameStartsWith(name);
        if (quotes.isEmpty()) return ResponseEntity.ok(MessageUtils.createResourceListIsEmptyMessage());
        else return ResponseEntity.ok(quotes);
    }

    @ApiOperation(value = "Lista todos os orçamentos que o nome do cliente contenha os o conjunto informado.", response = Quote[].class, responseContainer = "List",consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Nenhum orçamento cadastrado."),
            @ApiResponse(code = 200,message = "Listagem de orçamentos")
    })
    @GetMapping(path = "/protected/quotes/client/name/{name}")
    public ResponseEntity listAllByClientNameEquals(@PathVariable String name) {
        List<Quote> quotes = this.service.findAllByClientNameContains(name);
        if (quotes.isEmpty()) return ResponseEntity.ok(MessageUtils.createResourceListIsEmptyMessage());
        else return ResponseEntity.ok(quotes);
    }

    @ApiOperation(value = "Lista todos os orçamentos que o veiculo possua a placa igual a informada.", response = Quote[].class, responseContainer = "List",consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Nenhum orçamento cadastrado."),
            @ApiResponse(code = 200,message = "Listagem de orçamentos")
    })
    @GetMapping(path = "/protected/quotes/vehicle/plate/{plate}")
    public ResponseEntity listAllByVehicle(@PathVariable String plate) {
        List<Quote> quotes = this.service.findAllByVehicleLicensePlate(plate);
        if (quotes.isEmpty()) return ResponseEntity.ok(MessageUtils.createResourceListIsEmptyMessage());
        else return ResponseEntity.ok(quotes);
    }

    @ApiOperation(value = "Lista todos os orçamentos que o veiculo possua a placa igual a informada.", response = Quote[].class, responseContainer = "List",consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Nenhum orçamento cadastrado."),
            @ApiResponse(code = 200,message = "Listagem de orçamentos")
    })
    @GetMapping(path = "/protected/quotes/vehicle")
    public ResponseEntity listAllByVehiclePlateStartsWith(@RequestParam String plate) {
        List<Quote> quotes = this.service.findAllByVehicleLicensePlateStartsWith(plate);
        if (quotes.isEmpty()) return ResponseEntity.ok(MessageUtils.createResourceListIsEmptyMessage());
        else return ResponseEntity.ok(quotes);
    }

    @ApiOperation(value = "Encontra um orçamento pelo seu ID.", response = Quote.class,consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Quando não for possível encontrar o orçamento informado"),
            @ApiResponse(code = 200,message = "Orçamento buscado.")
    })
    @GetMapping(path = "/protected/quote/{id}")
    public ResponseEntity findOne(@ApiParam(value = "Id do orçamento", required = true, example = "3") @PathVariable Long id) {
        Optional<Quote> one = this.service.findOne(id);
        if (!one.isPresent()) return ResponseEntity.ok(MessageUtils.createResourceEntityNotPresentMessage(id));
        return ResponseEntity.ok(one.get());
    }

    @ApiOperation(value = "Cria um novo orçamento.",  response = Quote.class,consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Quando algumas das informações necessárias for vazia ou nula."),
            @ApiResponse(code = 200,message = "Orçamento criado.")
    })
    @PostMapping(path = "/admin/quote")
    public ResponseEntity saveOne(@RequestBody @Valid Quote quote,@ApiIgnore Errors errors) {
        if (errors.getFieldErrorCount() > 0) return ResponseEntity.ok(MessageUtils.createFieldErrorMessage(errors));
        if (Objects.isNull(quote)) return ResponseEntity.ok(MessageUtils.createRequestBodyIsMissingMessage());
        else return ResponseEntity.ok(this.service.saveOne(quote));
    }

    @ApiOperation(value = "Atualiza os dados de um orçamento", response = Quote.class,consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Quando não for possível encontrar o orçamento informado"),
            @ApiResponse(code = 400, message = "Quando os parametros informados estiverem em desacordo; algumas das informações necessárias for vazia ou nula"),
            @ApiResponse(code = 200,message = "Orçamento atualizado.")

    })
    @PutMapping(path = "/admin/quote/{id}")
    public ResponseEntity updateOne(@ApiParam(value = "Id do orçamento", required = true, example = "3") @PathVariable Long id, @RequestBody @Valid Quote quote,@ApiIgnore Errors errors) {
        if (errors.getFieldErrorCount() > 0) return ResponseEntity.ok(MessageUtils.createFieldErrorMessage(errors));
        if (id != quote.getId()) return ResponseEntity.ok(MessageUtils.createRequestConflictErrorMessage());
        else return ResponseEntity.ok(this.service.updateOne(id, quote));
    }

    @ApiOperation(value = "Remove um orçamento.",consumes = "application/json",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Quando não for possível encontrar o orçamento informado"),
            @ApiResponse(code = 200, message = "Orçamento deletado.")
    })
    @DeleteMapping(path = "/admin/quote/{id}")
    public ResponseEntity deleteOne(@ApiParam(value = "Id do orçamento", required = true, example = "3") @PathVariable Long id) {
        if (!this.service.findOne(id).isPresent()) return ResponseEntity.ok(MessageUtils.createResourceEntityNotPresentMessage(id));
        else return ResponseEntity.ok(MessageUtils.createDeleteSuccessMessage("Orçamento"));
    }

}
