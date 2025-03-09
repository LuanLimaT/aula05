package com.example.aula03.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate data;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemVenda> listaItens;

    @ManyToOne
    private Pessoa pessoa;

    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String estado;
    private String metodoPagamento;

    public BigDecimal total() {
        BigDecimal totalVenda = BigDecimal.ZERO;

        for (ItemVenda item : listaItens) {
            totalVenda = totalVenda.add(item.total());
        }
        return totalVenda.setScale(2, RoundingMode.HALF_UP);
    }
}