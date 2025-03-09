package com.example.aula03.model.repository;

import com.example.aula03.model.entity.ItemVenda;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemVendaRepository {
    @PersistenceContext
    private EntityManager em;

    public List<ItemVenda> itensVendas(){
        // Query é uma interface de consulta
        // As referências agora são apontadas para o Objeto e não ao Banco
        Query query = em.createQuery("from ItemVenda");
        return query.getResultList();
    }

    public void save(ItemVenda itemVenda){
        em.persist(itemVenda);
    }

    public ItemVenda itemVenda(Long id){
        return em.find(ItemVenda.class, id);
    }

    public void remove(Long id){
        ItemVenda v = em.find(ItemVenda.class, id);
        em.remove(v);
    }

    public void update(ItemVenda itemVenda){
        em.merge(itemVenda);
    }
}
