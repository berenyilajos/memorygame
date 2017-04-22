package hu.icell.dao;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import hu.icell.entities.Result;
import hu.icell.entities.User;


public class ResultDao {
    
    @PersistenceContext(unitName="memorygame")
    private EntityManager em;
    
    public List<Result> getResults() {
        Query q = em.createQuery("SELECT r FROM Result r JOIN FETCH r.user u ORDER BY r.seconds ASC, r.resultDate DESC");
        q.setMaxResults(20);
        List<Result> list = q.getResultList();
        
        return list;
    }
    
    public List<Result> getResultsByUser(User user) {
        Query q = em.createQuery("SELECT r FROM Result r JOIN FETCH r.user u WHERE u=:user ORDER BY r.seconds ASC, r.resultDate DESC");
        q.setParameter("user", user);
        q.setMaxResults(20);
        List<Result> list = q.getResultList();
        
        return list;
    }
    
    @Transactional
    public void saveResult(int seconds, long userId) {
        Result r = new Result();
        r.setResultDate(Calendar.getInstance().getTime());
        r.setSeconds(BigDecimal.valueOf(seconds));
        r.setUser(em.find(User.class, userId));
        em.persist(r);
    }

}
