package com.fiap.geoguardian.mapper;

import com.fiap.geoguardian.dto.UsuarioRequestDTO;
import com.fiap.geoguardian.model.*;
import com.fiap.geoguardian.repository.AlertaRepository;
import com.fiap.geoguardian.repository.TipoUsuarioRepository;
import com.fiap.geoguardian.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private AlertaRepository alertaRepository;

    public Usuario toEntity(UsuarioRequestDTO dto, Usuario usuarioExistente) {
        Usuario usuario = usuarioExistente != null ? usuarioExistente : new Usuario();

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());

        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(dto.getTipoUsuarioId())
                .orElseThrow(() -> new RuntimeException("Tipo de usuário não encontrado"));
        usuario.setTipoUsuario(tipoUsuario);

        if (dto.getAlertasIds() != null) {
            List<AlertaUsuario> alertas = dto.getAlertasIds().stream().map(id -> {
                Alerta alerta = alertaRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Alerta não encontrado: " + id));
                return new AlertaUsuario("EMAIL", "N", usuario, alerta);
            }).collect(Collectors.toList());
            usuario.setAlertasUsuario(alertas);
        }

        // Retornos serão preenchidos no service se necessário

        return usuario;
    }
}
