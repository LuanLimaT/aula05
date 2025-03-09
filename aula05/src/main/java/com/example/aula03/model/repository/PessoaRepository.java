package com.example.aula03.model.repository;

import com.example.aula03.model.entity.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PessoaRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Pessoa> pessoas() {
        Query query = em.createQuery("from Pessoa");
        return query.getResultList();
    }

    public Pessoa save(Pessoa pessoa) {
        if (pessoa.getId() == null) {
            em.persist(pessoa);
            return pessoa;
        } else {
            return em.merge(pessoa);
        }
    }

    public Pessoa pessoa(Long id) {
        return em.find(Pessoa.class, id);
    }

    public void remove(Long id) {
        Pessoa p = em.find(Pessoa.class, id);
        if (p != null) {
            em.remove(p);
        }
    }

    public void update(Pessoa pessoa) {
        em.merge(pessoa);
    }

    public Pessoa pessoaPorUsername(String username) {
        try {
            Query query = em.createQuery(
                    "select p from Pessoa p join p.usuario u where u.login = :username");
            query.setParameter("username", username);
            return (Pessoa) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Pessoa> pessoas(String nome) {
        Query query = em.createQuery("from Pessoa p where p.nome ilike :nome");
        query.setParameter("nome", "%" + nome + "%");
        return query.getResultList();
    }

}
