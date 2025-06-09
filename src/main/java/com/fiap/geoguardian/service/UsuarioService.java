package com.fiap.geoguardian.service;

import com.fiap.geoguardian.dto.UsuarioRequestDTO;
import com.fiap.geoguardian.mapper.UsuarioMapper;
import com.fiap.geoguardian.model.RetornoUsuario;
import com.fiap.geoguardian.model.Usuario;
import com.fiap.geoguardian.repository.AlertaRepository;
import com.fiap.geoguardian.repository.RetornoUsuarioRepository;
import com.fiap.geoguardian.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RetornoUsuarioRepository retornoUsuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<Usuario> findAll(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    public Page<Usuario> findByNome(String nome, Pageable pageable) {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    public Page<Usuario> findByTipoUsuarioId(Long tipoUsuarioId, Pageable pageable) {
        return usuarioRepository.findByTipoUsuarioId(tipoUsuarioId, pageable);
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario save(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Já existe um usuário com este email");
        }

        Usuario usuario = usuarioMapper.toEntity(dto, null);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        if (dto.getRetornosIds() != null) {
            List<RetornoUsuario> retornos = retornoUsuarioRepository.findAllById(dto.getRetornosIds());
            retornos.forEach(r -> r.setUsuario(usuario));
            usuario.setRetornosUsuario(retornos);
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario update(Long id, UsuarioRequestDTO dto) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (usuarioRepository.existsByEmail(dto.getEmail()) &&
                !usuarioExistente.getEmail().equals(dto.getEmail())) {
            throw new RuntimeException("Já existe um usuário com este email");
        }

        Usuario usuario = usuarioMapper.toEntity(dto, usuarioExistente);
        usuario.setId(id);
        usuario.setDataCadastro(usuarioExistente.getDataCadastro());

        if (!passwordEncoder.matches(dto.getSenha(), usuarioExistente.getSenha())) {
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        } else {
            usuario.setSenha(usuarioExistente.getSenha());
        }

        if (dto.getRetornosIds() != null) {
            List<RetornoUsuario> retornos = retornoUsuarioRepository.findAllById(dto.getRetornosIds());
            retornos.forEach(r -> r.setUsuario(usuario));
            usuario.setRetornosUsuario(retornos);
        }

        return usuarioRepository.save(usuario);
    }

    public void deleteById(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
