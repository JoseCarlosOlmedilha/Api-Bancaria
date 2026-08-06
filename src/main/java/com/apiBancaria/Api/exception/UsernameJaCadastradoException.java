package com.apiBancaria.Api.exception;

public class UsernameJaCadastradoException extends RuntimeException {

    public UsernameJaCadastradoException(String username) {
        super("Username " + username + " já está cadastrado");
    }

}
