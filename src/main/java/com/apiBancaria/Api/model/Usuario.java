package com.apiBancaria.Api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Usuario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private String dataNascimento;
    private String sexo;
    private StatusUsuario statusUsuario;


    private Endereco endereco;

    
    private Login login;

}
