package com.example.aula03.model.repository;

import com.example.aula03.model.entity.Venda;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class VendaRepository {
    @PersistenceContext
    private EntityManager em;

    public List<Venda> vendas(){
        // Query é uma interface de consulta
        // As referências agora são apontadas para o Objeto e não ao Banco
        Query query = em.createQuery("from Venda");
        return query.getResultList();
    }

    public void save(Venda venda){
        em.persist(venda);
    }

    public Venda venda(Long id){
        return em.find(Venda.class, id);
    }

    public void remove(Long id){
        Venda v = em.find(Venda.class, id);
        em.remove(v);
    }

    public void update(Venda venda){
        em.merge(venda);
    }

    public List<Venda> findAll() {
        Query query = em.createQuery("from Venda");
        return query.getResultList();
    }

    public List<Venda> findByPessoaUsername(String username) {
        // Ajuste a consulta de acordo com o relacionamento e estrutura do seu banco de dados
        Query query = em.createQuery(
                "SELECT v FROM Venda v WHERE v.pessoa.usuario.login = :username"
        );
        query.setParameter("username", username);
        return query.getResultList();
    }

    public List<Venda> vendas(String nomeCliente){
        Query query = em.createQuery("from Venda v where v.pessoa.nome ilike :nomeCliente");
        query.setParameter("nomeCliente", "%" + nomeCliente + "%");
        return query.getResultList();
    }

    public List<Venda> data(String dataInicio, String dataFim){
        LocalDate inicio = LocalDate.parse(dataInicio);
        LocalDate fim = LocalDate.parse(dataFim);

        Query query = em.createQuery("from Venda v where v.data between :dataInicio and :dataFim");
        query.setParameter("dataInicio", inicio);
        query.setParameter("dataFim", fim);
        return query.getResultList();
    }
}
