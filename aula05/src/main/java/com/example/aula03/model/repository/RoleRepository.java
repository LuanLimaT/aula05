package com.example.aula03.model.repository;

import com.example.aula03.model.entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleRepository {

    @PersistenceContext
    private EntityManager em;

    public Role findByName(String roleName) {
        TypedQuery<Role> query = em.createQuery(
                "from Role where nome = :roleName", Role.class);
        query.setParameter("roleName", roleName);
        List<Role> result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    public Role findById(Long id) {
        return em.find(Role.class, id);
    }

    public void save(Role role) {
        if (role.getId() == null) {
            em.persist(role);
        } else {
            em.merge(role);
        }
    }

    public void delete(Long id) {
        Role role = em.find(Role.class, id);
        if (role != null) {
            em.remove(role);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Role> findAll() {
        return em.createQuery("from Role").getResultList();
    }
}
