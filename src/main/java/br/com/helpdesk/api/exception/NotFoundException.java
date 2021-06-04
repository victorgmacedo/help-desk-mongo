package br.com.helpdesk.api.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String message){
        super(message, null, true, false);
    }

}
