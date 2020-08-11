package br.com.sg.mechanical.resource;

import br.com.sg.mechanical.domain.Employee;
import br.com.sg.mechanical.error.ErrorDetails;
import br.com.sg.mechanical.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1")
public class EmployeeAPI {

    private EmployeeService service;

    @Autowired
    public EmployeeAPI(EmployeeService service) {
        this.service = service;
    }

    @GetMapping(path = "/protected/employees")
    public ResponseEntity listAll() {
        List<Employee> all = this.service.findAll();
        if (all.isEmpty()) return ResponseEntity.ok(
                ErrorDetails.Builder.anErrorDetails()
                        .title("Nenhum funcionário encontrado!")
                        .status(404)
                        .timestamp(Date.from(Instant.now()).getTime())
                        .detail(String.format("Nenhum recurso encontrado"))
                        .developerMessage("")
                        .build()
        );
        else return ResponseEntity.ok(all);
    }

    @GetMapping(path = "/protected/employee/{id}")
    public ResponseEntity findOne(@PathVariable Long id) {
        Optional<Employee> one = this.service.findOne(id);
        if (one.isPresent()) return ResponseEntity.ok(one.get());
        else return ResponseEntity.ok(
                ErrorDetails.Builder.anErrorDetails()
                        .title("Funcionário não contrado!")
                        .status(404)
                        .timestamp(Date.from(Instant.now()).getTime())
                        .detail(String.format("Funcionario não encontrado para o ID: %s",id))
                        .developerMessage("DEVELOPMENT")
                        .build()
        );
    }
}
