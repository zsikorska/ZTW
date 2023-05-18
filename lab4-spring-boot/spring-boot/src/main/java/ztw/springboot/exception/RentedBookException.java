package ztw.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RentedBookException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;

    public RentedBookException(Long id){
        super("Book with id='" + id + "' is already rented");
    }
}
