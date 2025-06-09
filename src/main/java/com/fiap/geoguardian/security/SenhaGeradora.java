package com.fiap.geoguardian.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SenhaGeradora {
    public static void main(String[] args) {
        String senha = "123456";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(senha);
        System.out.println("Hash gerado para '123456':");
        System.out.println(hash);
    }
}
