package riccardo.U5W3D1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import riccardo.U5W3D1.entities.Employee;
import riccardo.U5W3D1.exceptions.BadRequestException;
import riccardo.U5W3D1.payloads.EmployeeDTO;
import riccardo.U5W3D1.services.EmployeeService;


import java.io.IOException;
import java.util.UUID;


@RestController
@RequestMapping ("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    private Page<Employee> getAllEmployee (@RequestParam (defaultValue = "0") int page, @RequestParam (defaultValue = "30") int size, @RequestParam (defaultValue = "username") String sortBy){
        return this.employeeService.getAllEmployee(page, size, sortBy);
    }

    @GetMapping ("/{employeeId}")
    private Employee getEmployeeById (@PathVariable UUID employeeId){
        return this.employeeService.findEmployeeById(employeeId);
    }

    @PutMapping ("/{employeeId}")
    private Employee findEmployeeAndUpdate (@PathVariable UUID employeeId, @RequestBody @Validated EmployeeDTO body, BindingResult validation){
        if (validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.employeeService.findEmployeeByIdAndUpdate(employeeId, body);
    }

    @DeleteMapping ("/{employeeId}")
    @ResponseStatus (HttpStatus.NO_CONTENT)
    private void deleteEmployee (@PathVariable UUID employeeId){
        this.employeeService.deleteEmployee(employeeId);
    }

    @PostMapping ("/avatar/{employeeId}")
    @ResponseStatus (HttpStatus.CREATED)
    private Employee uploadImage (@RequestParam ("avatar") MultipartFile image, @PathVariable UUID employeeId) throws IOException {
        return employeeService.uploadImage(image, employeeId);
    }
}
