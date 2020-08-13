package br.com.sg.mechanical.resource;

import br.com.sg.mechanical.domain.Employee;
import br.com.sg.mechanical.utils.ErrorUtils;
import br.com.sg.mechanical.service.EmployeeService;
import br.com.sg.mechanical.utils.FieldErrorMessageFormatter;
import br.com.sg.mechanical.utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
                ErrorUtils.createResourceListIsEmptyMessage()
        );
        else return ResponseEntity.ok(all);
    }

    @GetMapping(path = "/protected/employee/{id}")
    public ResponseEntity findOne(@PathVariable Long id) {
        Optional<Employee> one = this.service.findOne(id);
        if (one.isPresent()) return ResponseEntity.ok(one.get());
        else return ResponseEntity.ok(ErrorUtils.createResourceEntityNotPresentMessage(id));
    }

    @PostMapping(path = "/admin/employee")
    public ResponseEntity saveOne(@RequestBody @Valid Employee employee, Errors errors) {

        if (errors.getFieldErrorCount() > 0) {
            return ResponseEntity.ok(ErrorUtils.createFieldErrorMessage(errors));
        }

        employee.setPassword(PasswordEncoder.encode(employee.getPassword()));
        try {
            Employee employeeStored = this.service.saveOne(employee);
            return ResponseEntity.ok(employeeStored);
        }catch (DataIntegrityViolationException ex) {
            return ResponseEntity.ok(ErrorUtils.createDataIntegrityErrorMessage(FieldErrorMessageFormatter.getConstraintField(ex)));
        }
    }

    @PutMapping(path = "/admin/employee/{id}")
    public ResponseEntity updateOne(@PathVariable Long id, @RequestBody @Valid Employee employee, Errors errors) {
        if (id != employee.getId()) {
            return ResponseEntity.ok(ErrorUtils.createRequestConflictErrorMessage());
        }
        if (errors.getFieldErrorCount() > 0) {
            return ResponseEntity.ok(ErrorUtils.createFieldErrorMessage(errors));
        }
        if (!this.service.exists(id)) {
            return ResponseEntity.ok(ErrorUtils.createResourceEntityNotPresentMessage(id));
        }
        return ResponseEntity.ok(this.service.updateOne(id, employee));
    }
}
