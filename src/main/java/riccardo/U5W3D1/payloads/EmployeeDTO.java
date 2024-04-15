package riccardo.U5W3D1.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record EmployeeDTO(
                          @NotEmpty(message = "L' username è obbligatorio")
                          @Size(min = 1 , max = 20, message = "L' username deve avere la lunghezza dei caratteri compresa tra 1 e 20")
                          String username,
                          @NotEmpty(message = "Il nome è obbligatorio")
                          @Size (min = 1 , max = 20, message = "Il nome deve avere la lunghezza dei caratteri compresa tra 1 e 20")
                          String name,
                          @NotEmpty(message = "Il cognome è obbligatorio")
                          @Size (min = 1 , max = 20, message = "Il cognome deve avere la lunghezza dei caratteri compresa tra 1 e 20")
                          String surname,
                          @NotEmpty(message = "L'email è obbligatoria")
                          @Email (message = "Email non valida")
                          String email,
                          @NotEmpty(message = "La password è obbligatoria")
                          @Size (min = 8 , message = "La password deve avere come minimo 8 caratteri")
                          String password) {
}
