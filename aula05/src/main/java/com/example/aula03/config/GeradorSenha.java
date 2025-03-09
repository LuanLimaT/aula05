package com.example.aula03.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorSenha {

    public static void main(String[] args) {
        //solicitando o encode para 123
        System.out.println(new BCryptPasswordEncoder().encode("user"));
        System.out.println(new BCryptPasswordEncoder().encode("admin"));
    }

}