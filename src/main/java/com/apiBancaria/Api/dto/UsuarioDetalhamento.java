package com.apiBancaria.Api.dto;

import com.apiBancaria.Api.model.Usuario;

public record UsuarioDetalhamento(
        long id,
        String nome,
        String email,
        String cpf,
        String telefone,
        String dataNascimento,
        String sexo,
        String rua,
        String numero,
        String bairro,
        String cidade,
        String cep
) {

    public UsuarioDetalhamento(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCpf(),
                usuario.getTelefone(),
                usuario.getDataNascimento(),
                usuario.getSexo(),
                usuario.getEndereco().getRua(),
                usuario.getEndereco().getNumero(),
                usuario.getEndereco().getBairro(),
                usuario.getEndereco().getCidade(),
                usuario.getEndereco().getCep()
        );
    }

}
