package com.apiBancaria.Api.validation.login;

import com.apiBancaria.Api.model.Login;

public interface LoginValidationStrategy {

    void validar(Login login);

}
