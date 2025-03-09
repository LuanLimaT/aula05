package com.example.aula03.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Component
@Scope("session")
@NoArgsConstructor @AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"produto_id", "venda_id"}))
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser maior que zero")
    private Double quantidade;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private Venda venda;

    public BigDecimal total() {
        BigDecimal quantidadeDecimal = BigDecimal.valueOf(quantidade);
        BigDecimal valorTotal = quantidadeDecimal.multiply(produto.getValor());
        return valorTotal.setScale(2);
    }

    public void adicionarItem(List<ItemVenda> itens, Produto produto, Double quantidade) {
        for (ItemVenda item : itens) {
            if (item.getProduto().getId().equals(produto.getId())) {
                item.setQuantidade(item.getQuantidade() + quantidade);
                return;
            }
        }
        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setProduto(produto);
        itemVenda.setQuantidade(quantidade);
        itens.add(itemVenda);
    }

    public void removerItem(List<ItemVenda> itens, Long produtoId) {
        itens.removeIf(item -> item.getProduto().getId().equals(produtoId));
    }

    public BigDecimal calcularTotal(List<ItemVenda> itens) {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemVenda item : itens) {
            total = total.add(item.total());
        }
        return total.setScale(2);
    }

    public long contarItens(List<ItemVenda> itensCarrinho) {
        return (long) itensCarrinho.stream()
                .mapToDouble(ItemVenda::getQuantidade)
                .sum();
    }
}
