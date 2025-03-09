package com.example.aula03.model.repository;

import com.example.aula03.model.entity.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProdutoRepository {
    @PersistenceContext
    private EntityManager em;

    public List<Produto> produtos(){
        // Query é uma interface de consulta
        // As referências agora são apontadas para o Objeto e não ao Banco
        Query query = em.createQuery("from Produto");
        return query.getResultList();
    }

    public void save(Produto produto){
        em.persist(produto);
    }

    public Produto produto(Long id){
        return em.find(Produto.class, id);
    }

    public void remove(Long id){
        Produto p = em.find(Produto.class, id);
        em.remove(p);
    }

    public void update(Produto produto){
        em.merge(produto);
    }

    public List<Produto> produtos(String descricao){
        Query query = em.createQuery("from Produto p where p.descricao ilike :descricao");
        query.setParameter("descricao", "%" + descricao + "%");
        return query.getResultList();
    }
}
