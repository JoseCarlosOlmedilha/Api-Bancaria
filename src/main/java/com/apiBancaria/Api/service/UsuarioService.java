package com.apiBancaria.Api.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.apiBancaria.Api.dto.UsuarioDetalhamento;
import com.apiBancaria.Api.exception.UsuarioJaDesativadoException;
import com.apiBancaria.Api.exception.UsuarioNaoEncontradoException;
import com.apiBancaria.Api.model.StatusUsuario;
import com.apiBancaria.Api.model.Usuario;
import com.apiBancaria.Api.repository.UsuarioRepository;
import com.apiBancaria.Api.validation.usuario.UsuarioValidationStrategy;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final List<UsuarioValidationStrategy> validacoes;

    public UsuarioService(UsuarioRepository usuarioRepository, List<UsuarioValidationStrategy> validacoes) {
        this.usuarioRepository = usuarioRepository;
        this.validacoes = validacoes;
    }

    public Page<UsuarioDetalhamento> listarTodos(Pageable pageable, StatusUsuario statusUsuario) {
        Page<Usuario> usuarios = statusUsuario != null
                ? usuarioRepository.findByStatusUsuario(statusUsuario, pageable)
                : usuarioRepository.findAll(pageable);

        return usuarios.map(UsuarioDetalhamento::new);
    }

    public UsuarioDetalhamento buscarPorId(Long id) {
        return usuarioRepository.findById(id).map(UsuarioDetalhamento::new).orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário com id " + id + " não encontrado"));
    }

    public UsuarioDetalhamento cadastrar(Usuario usuario) {
        validacoes.forEach(validacao -> validacao.validar(usuario));
        return new UsuarioDetalhamento(usuarioRepository.save(usuario));
    }

    public void deletar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário com id " + id + " não encontrado"));

        if (usuario.getStatusUsuario() == StatusUsuario.Desativado) {
            throw new UsuarioJaDesativadoException(id);
        }

        usuario.setStatusUsuario(StatusUsuario.Desativado);
        usuarioRepository.save(usuario);
    }

}
