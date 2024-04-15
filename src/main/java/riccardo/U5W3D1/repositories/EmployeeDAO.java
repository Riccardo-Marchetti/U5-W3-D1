package riccardo.U5W3D1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import riccardo.U5W3D1.entities.Employee;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeDAO  extends JpaRepository<Employee, UUID> {
    Optional<Employee> findByEmail (String email);
}
