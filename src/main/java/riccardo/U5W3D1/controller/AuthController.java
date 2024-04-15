package riccardo.U5W3D1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import riccardo.U5W3D1.entities.Employee;
import riccardo.U5W3D1.exceptions.BadRequestException;
import riccardo.U5W3D1.payloads.AuthDTO;
import riccardo.U5W3D1.payloads.AuthResponseDTO;
import riccardo.U5W3D1.payloads.EmployeeDTO;
import riccardo.U5W3D1.services.AuthService;
import riccardo.U5W3D1.services.EmployeeService;

@RestController
@RequestMapping ("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private EmployeeService employeeService;

    // ENDPOINT PER LOGIN
    @PostMapping ("/login")
    public AuthResponseDTO login (@RequestBody AuthDTO payload){
        return new AuthResponseDTO(this.authService.authenticationEmployeeAndGenerateToken(payload));
    }
    @PostMapping ("/register")
    @ResponseStatus(HttpStatus.CREATED)
    private Employee saveEmployee (@RequestBody @Validated EmployeeDTO body, BindingResult validation){
        if (validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.employeeService.saveEmployee(body);
    }
}
