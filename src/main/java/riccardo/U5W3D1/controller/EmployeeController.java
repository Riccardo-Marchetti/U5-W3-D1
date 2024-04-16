package riccardo.U5W3D1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public Page<Employee> getAllEmployee (@RequestParam (defaultValue = "0") int page, @RequestParam (defaultValue = "30") int size, @RequestParam (defaultValue = "username") String sortBy){
        return this.employeeService.getAllEmployee(page, size, sortBy);
    }

    @GetMapping ("/me")
    public Employee getProfile (@AuthenticationPrincipal Employee currentUser){
        return currentUser;
    }

    @PutMapping ("/me")
    public Employee updateProfile (@AuthenticationPrincipal Employee currentUser, @RequestBody @Validated EmployeeDTO body ){
        return this.employeeService.findEmployeeByIdAndUpdate(currentUser.getId(), body);
    }

    @DeleteMapping ("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile (@AuthenticationPrincipal Employee currentUser){
        this.employeeService.deleteEmployee(currentUser.getId());
    }

    @GetMapping ("/{employeeId}")
    public Employee getEmployeeById (@PathVariable UUID employeeId){
        return this.employeeService.findEmployeeById(employeeId);
    }

    @PutMapping ("/{employeeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Employee findEmployeeAndUpdate (@PathVariable UUID employeeId, @RequestBody @Validated EmployeeDTO body, BindingResult validation){
        if (validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.employeeService.findEmployeeByIdAndUpdate(employeeId, body);
    }

    @DeleteMapping ("/{employeeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus (HttpStatus.NO_CONTENT)
    public void deleteEmployee (@PathVariable UUID employeeId){
        this.employeeService.deleteEmployee(employeeId);
    }

    @PostMapping ("/avatar/{employeeId}")
    @ResponseStatus (HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Employee uploadImage (@RequestParam ("avatar") MultipartFile image, @PathVariable UUID employeeId) throws IOException {
        return employeeService.uploadImage(image, employeeId);
    }
}
