package riccardo.U5W3D1.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException{
    public NotFoundException (UUID id){
        super("Il record con id: " + id + " non è stato trovato");
    }

    public NotFoundException (String message) { super(message);}
}
