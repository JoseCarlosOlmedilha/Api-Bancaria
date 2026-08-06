package com.apiBancaria.Api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.apiBancaria.Api.exception.LoginNaoEncontradoException;
import com.apiBancaria.Api.model.Login;
import com.apiBancaria.Api.repository.LoginRepository;
import com.apiBancaria.Api.validation.login.LoginValidationStrategy;

@Service
public class LoginService {

    private final LoginRepository loginRepository;
    private final List<LoginValidationStrategy> validacoes;

    public LoginService(LoginRepository loginRepository, List<LoginValidationStrategy> validacoes) {
        this.loginRepository = loginRepository;
        this.validacoes = validacoes;
    }

    public Login buscarPorId(Long id) {
        return loginRepository.findById(id)
                .orElseThrow(() -> new LoginNaoEncontradoException("Login com id " + id + " não encontrado"));
    }

    public Login cadastrar(Login login) {
        validacoes.forEach(validacao -> validacao.validar(login));
        return loginRepository.save(login);
    }

    public Login trocarUsername(Long id, String novoUsername) {
        Login login = buscarPorId(id);
        login.setUsername(novoUsername);
        validacoes.forEach(validacao -> validacao.validar(login));
        return loginRepository.save(login);
    }

    public Login trocarSenha(Long id, String novaSenha) {
        Login login = buscarPorId(id);
        login.setPassword(novaSenha);
        return loginRepository.save(login);
    }

}