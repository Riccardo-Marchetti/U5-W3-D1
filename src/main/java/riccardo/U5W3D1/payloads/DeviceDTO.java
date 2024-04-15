package riccardo.U5W3D1.payloads;

import jakarta.validation.constraints.*;

public record DeviceDTO(
                        @NotEmpty(message = "Il tipo è obbligatorio")
                        @Pattern (regexp = "Smartphone|Tablet|Laptop|smartphone|tablet|laptop", message = "Inserisci un tipo valido tra Smartphone, Tablet, Laptop")
                        String type) {
}
