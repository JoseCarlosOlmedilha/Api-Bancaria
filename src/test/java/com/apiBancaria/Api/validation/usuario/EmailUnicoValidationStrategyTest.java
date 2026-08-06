package com.apiBancaria.Api.validation.usuario;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.apiBancaria.Api.exception.EmailJaCadastradoException;
import com.apiBancaria.Api.model.Usuario;
import com.apiBancaria.Api.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class EmailUnicoValidationStrategyTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    private EmailUnicoValidationStrategy validacao;

    @BeforeEach
    void setUp() {
        validacao = new EmailUnicoValidationStrategy(usuarioRepository);
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaEstiverCadastrado() {
        Usuario usuario = new Usuario();
        usuario.setEmail("joao@email.com");
        when(usuarioRepository.existsByEmail("joao@email.com")).thenReturn(true);

        assertThatThrownBy(() -> validacao.validar(usuario))
                .isInstanceOf(EmailJaCadastradoException.class);
    }

    @Test
    void naoDeveLancarExcecaoQuandoEmailNaoEstiverCadastrado() {
        Usuario usuario = new Usuario();
        usuario.setEmail("novo@email.com");
        when(usuarioRepository.existsByEmail("novo@email.com")).thenReturn(false);

        assertThatCode(() -> validacao.validar(usuario)).doesNotThrowAnyException();
    }

}
