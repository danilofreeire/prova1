package dao;

import config.JPAUtil;
import domain.Aluno;
import jakarta.persistence.EntityManager;

import java.util.List;

public class AlunoDAO {
    public void salvar(Aluno a) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        if (a.getId() == null) em.persist(a); else em.merge(a);
        em.getTransaction().commit();
        em.close();
    }

    public void excluir(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        Aluno ref = em.find(Aluno.class, id);
        if (ref != null) em.remove(ref);
        em.getTransaction().commit();
        em.close();
    }

    public List<Aluno> listar() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Aluno> lista = em.createQuery("select a from Aluno a order by a.nome", Aluno.class).getResultList();
        em.close();
        return lista;
    }
}
