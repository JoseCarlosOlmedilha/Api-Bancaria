package com.apiBancaria.Api.exception;

public class UsuarioJaDesativadoException extends RuntimeException {

    public UsuarioJaDesativadoException(long id) {
        super("Usuário com id " + id + " já está desativado");
    }

}
