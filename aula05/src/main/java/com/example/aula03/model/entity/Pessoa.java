package com.example.aula03.model.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public abstract class Pessoa implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;

    @OneToMany(mappedBy = "pessoa")
    private List<Venda> vendas;

    @OneToOne(mappedBy = "pessoa", cascade = CascadeType.ALL)
    @Valid
    private Usuario usuario;

    public abstract String dadosClasse();




}
