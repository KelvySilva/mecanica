package br.com.sg.mechanical.repository;

import br.com.sg.mechanical.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
