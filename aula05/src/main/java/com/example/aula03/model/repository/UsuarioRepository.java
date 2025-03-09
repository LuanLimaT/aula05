package com.example.aula03.model.repository;

import com.example.aula03.model.entity.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsuarioRepository {

    @PersistenceContext
    private EntityManager em;


    public Usuario findByLogin(String login) {
        TypedQuery<Usuario> query = em.createQuery(
                "from Usuario where login = :login", Usuario.class);
        query.setParameter("login", login);
        List<Usuario> result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    public Usuario findById(Long id) {
        return em.find(Usuario.class, id);
    }

    public void save(Usuario usuario) {
        if (usuario.getId() == null) {
            em.persist(usuario);
        } else {
            em.merge(usuario);
        }
    }

    public void delete(Long id) {
        Usuario usuario = em.find(Usuario.class, id);
        if (usuario != null) {
            em.remove(usuario);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Usuario> findAll() {
        Query query = em.createQuery("from Usuario");
        return query.getResultList();
    }
}
