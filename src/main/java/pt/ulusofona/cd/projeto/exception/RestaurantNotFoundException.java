package pt.ulusofona.cd.projeto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class RestaurantNotFoundException extends RuntimeException{

    public RestaurantNotFoundException (String message){
        super (message);
    }

}
