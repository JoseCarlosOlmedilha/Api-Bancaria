package com.apiBancaria.Api.exception;

public class LoginNaoEncontradoException extends RuntimeException {

    public LoginNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

}
