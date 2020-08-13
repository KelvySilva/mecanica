package br.com.sg.mechanical.resource;

import br.com.sg.mechanical.domain.Quote;
import br.com.sg.mechanical.utils.ErrorUtils;
import br.com.sg.mechanical.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("v1")
public class QuoteAPI {

    private QuoteService service;

    @Autowired
    public QuoteAPI(QuoteService service) {
        this.service = service;
    }

    @GetMapping(path = "/protected/quotes")
    public ResponseEntity listAll() {
        List<Quote> quotes = this.service.listAll();
        if (quotes.isEmpty()) return ResponseEntity.ok(ErrorUtils.createResourceListIsEmptyMessage());
        else return ResponseEntity.ok(quotes);
    }

    @GetMapping(path = "/protected/quote/{id}")
    public ResponseEntity findOne(@PathVariable Long id) {
        Optional<Quote> one = this.service.findOne(id);
        if (!one.isPresent()) return ResponseEntity.ok(ErrorUtils.createResourceEntityNotPresentMessage(id));
        return ResponseEntity.ok(one.get());
    }

    @PostMapping(path = "/admin/quote")
    public ResponseEntity saveOne(@RequestBody @Valid Quote quote, Errors errors) {
        if (errors.getFieldErrorCount() > 0) return ResponseEntity.ok(ErrorUtils.createFieldErrorMessage(errors));
        if (Objects.isNull(quote)) return ResponseEntity.ok(ErrorUtils.createRequestBodyIsMissingMessage());
        else return ResponseEntity.ok(this.service.saveOne(quote));
    }

    @PutMapping(path = "/admin/quote/{id}")
    public ResponseEntity updateOne(@PathVariable Long id, @RequestBody @Valid Quote quote, Errors errors) {
        if (errors.getFieldErrorCount() > 0) return ResponseEntity.ok(ErrorUtils.createFieldErrorMessage(errors));
        if (id != quote.getId()) return ResponseEntity.ok(ErrorUtils.createRequestConflictErrorMessage());
        else return ResponseEntity.ok(this.service.updateOne(id, quote));
    }

}
