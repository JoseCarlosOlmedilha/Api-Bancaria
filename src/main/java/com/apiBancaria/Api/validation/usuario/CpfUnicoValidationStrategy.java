package com.apiBancaria.Api.validation.usuario;

import org.springframework.stereotype.Component;

import com.apiBancaria.Api.exception.CpfJaCadastradoException;
import com.apiBancaria.Api.model.Usuario;
import com.apiBancaria.Api.repository.UsuarioRepository;

@Component
public class CpfUnicoValidationStrategy implements UsuarioValidationStrategy {

    private final UsuarioRepository usuarioRepository;

    public CpfUnicoValidationStrategy(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void validar(Usuario usuario) {
        if (usuarioRepository.existsByCpf(usuario.getCpf())) {
            throw new CpfJaCadastradoException(usuario.getCpf());
        }
    }

}
