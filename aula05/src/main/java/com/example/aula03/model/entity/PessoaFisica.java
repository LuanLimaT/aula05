package com.example.aula03.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Entity
@DiscriminatorValue("F")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PessoaFisica extends Pessoa{

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
    private String cpf;

    @Override
    public String dadosClasse() {
        return cpf;
    }

}
