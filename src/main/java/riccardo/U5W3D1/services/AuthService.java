package riccardo.U5W3D1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import riccardo.U5W3D1.entities.Employee;
import riccardo.U5W3D1.exceptions.UnauthorizedException;
import riccardo.U5W3D1.payloads.AuthDTO;
import riccardo.U5W3D1.security.JWTTools;

@Service
public class AuthService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JWTTools jwtTools;

    public String authenticationEmployeeAndGenerateToken (AuthDTO payload){
    // 1 controllo le credenziali
    // 1.1 cerco nel db tramite l’email l’utente
        Employee employee = this.employeeService.findByEmail(payload.email());

    // 1.2 verifico se la password combacia con quella ricevuta nel payload
        if (employee.getPassword().equals(payload.password())){
            // 2 se tutto è ok, genero un token e lo torno
            return jwtTools.createToken(employee);
        } else {
        // 3 se le credenziali non fossero ok —> 401 (Unauthorized)
            throw new UnauthorizedException("Credenziali errate! effettua di nuovo il login");
        }


    }
}
