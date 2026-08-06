package com.apiBancaria.Api.validation.usuario;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.apiBancaria.Api.exception.CpfJaCadastradoException;
import com.apiBancaria.Api.model.Usuario;
import com.apiBancaria.Api.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class CpfUnicoValidationStrategyTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    private CpfUnicoValidationStrategy validacao;

    @BeforeEach
    void setUp() {
        validacao = new CpfUnicoValidationStrategy(usuarioRepository);
    }

    @Test
    void deveLancarExcecaoQuandoCpfJaEstiverCadastrado() {
        Usuario usuario = new Usuario();
        usuario.setCpf("12345678900");
        when(usuarioRepository.existsByCpf("12345678900")).thenReturn(true);

        assertThatThrownBy(() -> validacao.validar(usuario))
                .isInstanceOf(CpfJaCadastradoException.class);
    }

    @Test
    void naoDeveLancarExcecaoQuandoCpfNaoEstiverCadastrado() {
        Usuario usuario = new Usuario();
        usuario.setCpf("98765432100");
        when(usuarioRepository.existsByCpf("98765432100")).thenReturn(false);

        assertThatCode(() -> validacao.validar(usuario)).doesNotThrowAnyException();
    }

}
