package riccardo.U5W3D1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import riccardo.U5W3D1.payloads.ErrorsDTO;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler (NotFoundException.class)
    @ResponseStatus (HttpStatus.NOT_FOUND)
    public ErrorsDTO handleNotFound (NotFoundException ex){
        return new ErrorsDTO(ex.getMessage(), LocalDateTime.now());
    }
    @ExceptionHandler (BadRequestException.class)
    @ResponseStatus (HttpStatus.BAD_REQUEST)
    public ErrorsDTO handleBadRequest (BadRequestException ex){
        if (ex.getErrorList() != null) {
            String message = ex.getErrorList().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(". "));
            return new ErrorsDTO(message, LocalDateTime.now());
        }
        return new ErrorsDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus (HttpStatus.UNAUTHORIZED)
    public ErrorsDTO handleUnauthorizedException(UnauthorizedException ex){
        return new ErrorsDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler (Exception.class)
    @ResponseStatus (HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsDTO handleException (Exception ex){
        ex.printStackTrace();
        return new ErrorsDTO("Problema relativo al server, risolveremo al più presto possibile", LocalDateTime.now());
    }

}
