package pt.ulusofona.cd.projeto.exception;

public class MenuItemNotFoundException extends RuntimeException{

    public MenuItemNotFoundException(String message){
        super(message);
    }

}
