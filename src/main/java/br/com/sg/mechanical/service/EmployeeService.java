package br.com.sg.mechanical.service;

import br.com.sg.mechanical.domain.Employee;
import br.com.sg.mechanical.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeeRepository repository;

    @Autowired
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> findAll() {
        return this.repository.findAll();
    }

    public Optional<Employee> findOne(Long id) {
        return this.repository.findById(id);
    }
}
