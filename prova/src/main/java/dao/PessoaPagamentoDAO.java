package dao;

import config.JPAUtil;
import domain.PessoaPagamento;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PessoaPagamentoDAO {

    public void salvar(PessoaPagamento p) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        if (p.getCodigo() == null) em.persist(p); else em.merge(p);
        em.getTransaction().commit();
        em.close();
    }

    public List<PessoaPagamento> listar() {
        EntityManager em = JPAUtil.getEntityManager();
        List<PessoaPagamento> lista = em.createQuery(
                "select p from PessoaPagamento p order by p.dataHora desc",
                PessoaPagamento.class
        ).getResultList();
        em.close();
        return lista;
    }
}
