package com.apiBancaria.Api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.apiBancaria.Api.model.StatusUsuario;
import com.apiBancaria.Api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);

    Page<Usuario> findByStatusUsuario(StatusUsuario statusUsuario, Pageable pageable);

}
