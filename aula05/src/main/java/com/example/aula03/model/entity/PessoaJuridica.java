package com.example.aula03.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@DiscriminatorValue("J")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PessoaJuridica extends Pessoa {

    @NotBlank(message = "CNPJ é obrigatório")
    @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter 14 dígitos")
    private String cnpj;

    @Override
    public String dadosClasse() {
        return cnpj;
    }


}
