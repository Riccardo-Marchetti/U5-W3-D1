package riccardo.U5W3D1.payloads;

import java.time.LocalDateTime;

public record ErrorsDTO(String message, LocalDateTime dateMessage) {
}
