package com.apiBancaria.Api.exception;

public class CpfJaCadastradoException extends RuntimeException {

    public CpfJaCadastradoException(String cpf) {
        super("CPF " + cpf + " já está cadastrado");
    }

}
