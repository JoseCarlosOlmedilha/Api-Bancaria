package com.apiBancaria.Api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.apiBancaria.Api.dto.UsuarioDetalhamento;
import com.apiBancaria.Api.exception.UsuarioJaDesativadoException;
import com.apiBancaria.Api.exception.UsuarioNaoEncontradoException;
import com.apiBancaria.Api.model.Endereco;
import com.apiBancaria.Api.model.StatusUsuario;
import com.apiBancaria.Api.model.Usuario;
import com.apiBancaria.Api.repository.UsuarioRepository;
import com.apiBancaria.Api.validation.usuario.UsuarioValidationStrategy;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioValidationStrategy validacao;

    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioService = new UsuarioService(usuarioRepository, List.of(validacao));
    }

    private Usuario criarUsuario() {
        Endereco endereco = new Endereco();
        endereco.setRua("Rua A");
        endereco.setNumero("123");
        endereco.setBairro("Centro");
        endereco.setCidade("Cidade");
        endereco.setCep("00000-000");

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João");
        usuario.setEmail("joao@email.com");
        usuario.setCpf("12345678900");
        usuario.setTelefone("11999999999");
        usuario.setDataNascimento("2000-01-01");
        usuario.setSexo("M");
        usuario.setStatusUsuario(StatusUsuario.Ativado);
        usuario.setEndereco(endereco);
        return usuario;
    }

    @Test
    void deveListarTodosOsUsuariosPaginadosQuandoStatusNaoInformado() {
        Usuario usuario = criarUsuario();
        Pageable pageable = PageRequest.of(0, 10);
        when(usuarioRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(usuario)));

        Page<UsuarioDetalhamento> resultado = usuarioService.listarTodos(pageable, null);

        assertThat(resultado.getContent()).hasSize(1);
        assertThat(resultado.getContent().get(0).nome()).isEqualTo("João");
    }

    @Test
    void deveListarUsuariosFiltrandoPorStatus() {
        Usuario usuario = criarUsuario();
        Pageable pageable = PageRequest.of(0, 10);
        when(usuarioRepository.findByStatusUsuario(StatusUsuario.Ativado, pageable))
                .thenReturn(new PageImpl<>(List.of(usuario)));

        Page<UsuarioDetalhamento> resultado = usuarioService.listarTodos(pageable, StatusUsuario.Ativado);

        assertThat(resultado.getContent()).hasSize(1);
        verify(usuarioRepository, never()).findAll(any(Pageable.class));
    }

    @Test
    void deveBuscarUsuarioPorId() {
        Usuario usuario = criarUsuario();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        UsuarioDetalhamento resultado = usuarioService.buscarPorId(1L);

        assertThat(resultado.id()).isEqualTo(1L);
        assertThat(resultado.email()).isEqualTo("joao@email.com");
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontradoAoBuscar() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.buscarPorId(99L))
                .isInstanceOf(UsuarioNaoEncontradoException.class);
    }

    @Test
    void deveExecutarValidacoesEChamarSaveAoCadastrar() {
        Usuario usuario = criarUsuario();
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        UsuarioDetalhamento resultado = usuarioService.cadastrar(usuario);

        verify(validacao, times(1)).validar(usuario);
        verify(usuarioRepository, times(1)).save(usuario);
        assertThat(resultado.email()).isEqualTo("joao@email.com");
    }

    @Test
    void naoDeveSalvarQuandoValidacaoLancarExcecao() {
        Usuario usuario = criarUsuario();
        doThrow(new RuntimeException("inválido")).when(validacao).validar(usuario);

        assertThatThrownBy(() -> usuarioService.cadastrar(usuario))
                .isInstanceOf(RuntimeException.class);

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoAoDeletarUsuarioInexistente() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.deletar(1L))
                .isInstanceOf(UsuarioNaoEncontradoException.class);
    }

    @Test
    void deveLancarExcecaoAoDeletarUsuarioJaDesativado() {
        Usuario usuario = criarUsuario();
        usuario.setStatusUsuario(StatusUsuario.Desativado);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        assertThatThrownBy(() -> usuarioService.deletar(1L))
                .isInstanceOf(UsuarioJaDesativadoException.class);

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void deveDesativarUsuarioAtivoAoDeletar() {
        Usuario usuario = criarUsuario();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        usuarioService.deletar(1L);

        assertThat(usuario.getStatusUsuario()).isEqualTo(StatusUsuario.Desativado);
        verify(usuarioRepository, times(1)).save(usuario);
    }

}
