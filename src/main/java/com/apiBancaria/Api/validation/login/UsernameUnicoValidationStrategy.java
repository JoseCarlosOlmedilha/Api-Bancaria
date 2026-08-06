package com.apiBancaria.Api.validation.login;

import org.springframework.stereotype.Component;

import com.apiBancaria.Api.exception.UsernameJaCadastradoException;
import com.apiBancaria.Api.model.Login;
import com.apiBancaria.Api.repository.LoginRepository;

@Component
public class UsernameUnicoValidationStrategy implements LoginValidationStrategy {

    private final LoginRepository loginRepository;

    public UsernameUnicoValidationStrategy(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public void validar(Login login) {
        boolean usernameEmUso = login.getId() != null
                ? loginRepository.existsByUsernameAndIdNot(login.getUsername(), login.getId())
                : loginRepository.existsByUsername(login.getUsername());

        if (usernameEmUso) {
            throw new UsernameJaCadastradoException(login.getUsername());
        }
    }

}
