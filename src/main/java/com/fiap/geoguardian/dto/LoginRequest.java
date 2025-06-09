package com.fiap.geoguardian.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de login do usuário")
public class LoginRequest {
    
    @Schema(description = "Email do usuário", example = "admin@geoguardian.com")
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter um formato válido")
    private String email;
    
    // Aceita tanto 'password' quanto 'senha' para compatibilidade
    @Schema(description = "Senha do usuário", example = "123456")
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 3, message = "Senha deve ter pelo menos 3 caracteres")
    private String password;
    
    @Schema(description = "Senha do usuário (compatibilidade)", example = "123456")
    private String senha;
    
    // Constructors
    public LoginRequest() {}
    
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
        this.senha = password; // Para compatibilidade
    }
    
    // Getters and Setters
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
        // Sincronizar com senha para compatibilidade
        if (this.senha == null) {
            this.senha = password;
        }
    }
    
    // Método para compatibilidade - retorna password se senha estiver null
    public String getSenha() {
        return senha != null ? senha : password;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
        // Sincronizar com password para compatibilidade
        if (this.password == null) {
            this.password = senha;
        }
    }
    
    @Override
    public String toString() {
        return "LoginRequest{" +
                "email='" + email + '\'' +
                ", password='[PROTECTED]'" +
                '}';
    }
}