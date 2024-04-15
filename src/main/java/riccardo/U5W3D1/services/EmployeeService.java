package riccardo.U5W3D1.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import riccardo.U5W3D1.entities.Employee;
import riccardo.U5W3D1.exceptions.BadRequestException;
import riccardo.U5W3D1.exceptions.NotFoundException;
import riccardo.U5W3D1.payloads.EmployeeDTO;
import riccardo.U5W3D1.repositories.EmployeeDAO;

import java.io.IOException;
import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private Cloudinary cloudinary;

    public Page<Employee> getAllEmployee (int page, int size, String sortBy){
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.employeeDAO.findAll(pageable);
    }

    public Employee findEmployeeById (UUID id){
        return  this.employeeDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Employee saveEmployee (EmployeeDTO body){
        this.employeeDAO.findByEmail(body.email()).ifPresent(
                employee -> {
                    throw new BadRequestException("l'email: " + body.email() + " è già in uso");
                }
        );
        Employee employee = new Employee(body.username(), body.name(), body.surname(), body.email(), body.password());
        employee.setAvatar("https://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());
        return this.employeeDAO.save(employee);
    }

    public Employee findEmployeeByIdAndUpdate (UUID id, EmployeeDTO body){
        Employee employee = this.employeeDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
        employee.setUsername(body.username());
        employee.setName(body.name());
        employee.setSurname(body.surname());
        employee.setEmail(body.email());
        employee.setAvatar("https://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());
        return this.employeeDAO.save(employee);
    }

    public void deleteEmployee (UUID id){
        Employee employee = this.employeeDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
        this.employeeDAO.delete(employee);
    }
    public Employee uploadImage (MultipartFile image, UUID id) throws IOException {
        Employee found = this.employeeDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
        String avatar = (String) cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setAvatar(avatar);
        return this.employeeDAO.save(found);
    }

    public Employee findByEmail (String email){
       return this.employeeDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("l'Utente con email: " + email + " non è stato trovato"));
    }
}
