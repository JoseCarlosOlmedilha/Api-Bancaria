package com.apiBancaria.Api.validation.usuario;

import org.springframework.stereotype.Component;

import com.apiBancaria.Api.exception.EmailJaCadastradoException;
import com.apiBancaria.Api.model.Usuario;
import com.apiBancaria.Api.repository.UsuarioRepository;

@Component
public class EmailUnicoValidationStrategy implements UsuarioValidationStrategy {

    private final UsuarioRepository usuarioRepository;

    public EmailUnicoValidationStrategy(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void validar(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new EmailJaCadastradoException(usuario.getEmail());
        }
    }

}
