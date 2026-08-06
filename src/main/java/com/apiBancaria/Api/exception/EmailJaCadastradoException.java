package com.apiBancaria.Api.exception;

public class EmailJaCadastradoException extends RuntimeException {

    public EmailJaCadastradoException(String email) {
        super("Email " + email + " já está cadastrado");
    }

}
