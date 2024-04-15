package riccardo.U5W3D1.exceptions;

import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BadRequestException extends RuntimeException{
    List<ObjectError> errorList = new ArrayList<>();
    public BadRequestException (String message){
        super(message);
    }
    public BadRequestException (List<ObjectError> errorList){
        super("Ci sono stati degli errori nella validazione del payload");
        this.errorList = errorList;
    }
}
